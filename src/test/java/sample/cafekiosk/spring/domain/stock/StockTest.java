package sample.cafekiosk.spring.domain.stock;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class StockTest {

    @Test
    void isQuantityLessThan() throws Exception {

        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        boolean result = stock.isQuantityLessThan(quantity);

        assertThat(result).isTrue();
    }

    @Test
    void deductQuantity() throws Exception {
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        stock.deductQuantity(quantity);

        assertThat(stock.getQuantity()).isZero();
    }

    @Test
    void deductQuantity2() throws Exception {
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        assertThatThrownBy(() -> {
            stock.deductQuantity(quantity);
        }).isInstanceOf(IllegalArgumentException.class);


    }

    @TestFactory
    Collection<DynamicTest> dynamicyTest() throws Exception {
        Stock stock = Stock.create("001", 1);

        return List.of(
            DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다." , () -> {
                int quantity = 1;

                stock.deductQuantity(quantity);

                assertThat(stock.getQuantity()).isZero();
            }),
            DynamicTest.dynamicTest("재고보다 많은 수량으로 차감 시도하는 경우 예외가 발생한다." , () -> {
                int quantity = 1;

                assertThatThrownBy(() -> stock.deductQuantity(quantity))
                    .isInstanceOf(IllegalArgumentException.class);
            })
        );
    }
}