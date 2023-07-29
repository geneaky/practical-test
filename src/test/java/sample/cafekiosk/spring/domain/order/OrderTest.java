package sample.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

class OrderTest {

    @Test
    void calculateTotalPrice() throws Exception {
        List<Product> products = List.of(createProduct("001", 1000), createProduct("002", 2000));
        Order order = Order.create(products, LocalDateTime.now());

        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    @Test
    void init() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        List<Product> products = List.of(createProduct("001", 1000), createProduct("002", 2000));
        Order order = Order.create(products, now);

        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
            .type(ProductType.HANDMADE)
            .productNumber(productNumber)
            .price(price)
            .sellingStatus(ProductSellingStatus.SELLING)
            .name("메뉴 이름")
            .build();
    }

}