package sample.cafekiosk.spring.domain.stock;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
}