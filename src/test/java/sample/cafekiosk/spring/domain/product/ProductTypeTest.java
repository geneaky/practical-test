package sample.cafekiosk.spring.domain.product;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

    @Test
    void containsStockType() throws Exception {
        boolean result = ProductType.containsStockType(ProductType.BAKERY);

        Assertions.assertThat(result).isTrue();
    }
}