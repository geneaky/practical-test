package sample.cafekiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumber();
        List<Product> products = findProductsBy(productNumbers);

        deductStockQuantities(products);

        Order order =  Order.create(products, registeredDateTime);

        Order saveOrder = orderRepository.save(order);
        return OrderResponse.of(saveOrder);
    }

    private void deductStockQuantities(List<Product> products) {
        List<String> stockProductNumbers = extractStockProductNumbers(products);

        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

        for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();
            if(stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("not enough stocks");
            }
            stock.deductQuantity(quantity);
        }
    }

    private Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
            .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        return stocks.stream()
            .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
    }

    private List<String> extractStockProductNumbers(List<Product> products) {
        return products.stream()
            .filter(product -> ProductType.containsStockType(product.getType()))
            .map(Product::getProductNumber)
            .collect(Collectors.toList());
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
            .map(productMap::get)
            .collect(Collectors.toList());
    }
}
