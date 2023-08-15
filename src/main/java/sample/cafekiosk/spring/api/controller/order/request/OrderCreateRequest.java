package sample.cafekiosk.spring.api.controller.order.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateRequest {

    @NotEmpty(message = "상품 목록 리스트는 필수입니다.")
    private List<String> productNumber;

    @Builder
    private OrderCreateRequest(List<String> productNumber) {
        this.productNumber = productNumber;
    }

    public OrderCreateServiceRequest toServiceRequest() {
        return OrderCreateServiceRequest.builder()
            .productNumber(this.productNumber)
            .build();
    }
}
