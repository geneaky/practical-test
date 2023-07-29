package sample.cafekiosk.spring.api.controller.order.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateRequest {

    private List<String> productNumber;

    @Builder
    private OrderCreateRequest(List<String> productNumber) {
        this.productNumber = productNumber;
    }
}
