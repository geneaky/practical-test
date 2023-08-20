package sample.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.STOP_SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

class OrderStatisticServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderStatisticService orderStatisticService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @AfterEach
    void tearDwon() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @Test
    void sendOrderStatsticsMail() throws Exception {
        LocalDateTime now = LocalDateTime.of(2023, 3, 5, 0, 0);

        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 1000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 2000);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 3000);
        productRepository.saveAll(List.of(product1, product2, product3));

        List<Product> productList = List.of(product1, product2, product3);
        Order order1 = createPaymentCompleteOrder(LocalDateTime.of(2023,3,4,23,59), productList);
        Order order2 = createPaymentCompleteOrder(now, productList);
        Order order3 = createPaymentCompleteOrder(LocalDateTime.of(2023,3,5,23,59), productList);
        Order order4 = createPaymentCompleteOrder(LocalDateTime.of(2023,3,6,0,0), productList);

        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        boolean result = orderStatisticService.sendOrderStatisticMail(LocalDate.of(2023, 3, 5),
            "test@test.com");

        assertThat(result).isTrue();
        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
            .extracting("content")
            .contains("총 매출 합계는 12000원입니다.");
    }

    private Order createPaymentCompleteOrder(LocalDateTime now, List<Product> productList) {
        Order order1 = Order.builder()
            .products(productList)
            .orderStatus(OrderStatus.PAYMENT_COMPLETE)
            .registeredDAteTime(now)
            .build();
        return orderRepository.save(order1);
    }

    private Product createProduct(String number, ProductType type, ProductSellingStatus status, String name, int price) {
        return Product.builder()
            .productNumber(number)
            .type(type)
            .sellingStatus(status)
            .name(name)
            .price(price)
            .build();
    }

}