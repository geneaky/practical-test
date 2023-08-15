package sample.cafekiosk.spring.api.service.order.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateServiceRequest {

    private List<String> productNumber;

    @Builder
    private OrderCreateServiceRequest(List<String> productNumber) {
        this.productNumber = productNumber;
    }
}
