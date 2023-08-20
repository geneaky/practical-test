package sample.cafekiosk.spring.domain.product;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTypeTest {

    @ParameterizedTest
    @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
    void containsStockType(ProductType productType, boolean expected) throws Exception {
        boolean result = ProductType.containsStockType(productType);

        Assertions.assertThat(result).isEqualTo(expected);
    }
}