package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.service.StockManager;
import store.util.ErrorCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    private StockManager stockManager;

    @BeforeEach
    void setUp() {
        stockManager = new StockManager();
    }

    @Test
    void 리스트에_없는_상품_주문() {
        // when & then
        assertThatThrownBy(() -> new Order(stockManager, "없는상품", 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    void 리스트에_있는_상품_주문() {
        // when
        Order order = new Order(stockManager, "콜라", 1);

        // then
        assertThat(order).isNotNull();
    }

    @ParameterizedTest
    @CsvSource({ "콜라,25", "할인끝난상품,6", "에너지바,10" })
    void 구매_가능_수량_초과(String productName, int purchase) {
        // when & then
        assertThatThrownBy(() -> new Order(stockManager, productName, purchase))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.QUANTITY_EXCEED_STOCK.getMessage());
    }

    @ParameterizedTest
    @CsvSource({ "콜라,20", "할인끝난상품,5", "에너지바,5" })
    void 구매_가능_수량(String productName, int purchase) {
        // when
        Order order = new Order(stockManager, productName, purchase);

        // then
        assertThat(order).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"20", "-6"})
    void 수량_조정_범위_초과(int amount) {
        // given
        Order order = new Order(stockManager, "콜라", 5);

        // when & then
        assertThatThrownBy(() -> order.addQuantity(amount))
                .isInstanceOf(InternalError.class)
                .hasMessage(ErrorCode.INTERNAL_ERROR.getMessage());
    }

}