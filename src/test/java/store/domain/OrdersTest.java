package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.service.StockManager;
import store.util.ErrorCode;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrdersTest {

    private StockManager stockManager;

    @BeforeEach
    void setUp() {
        stockManager = new StockManager();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[사이다-2][콜라-1]", "[사이다-2] [콜라-1]",
            "[사이다-2", "사이다-2]", "사이다-2",
            "[사이다 2]", "[사이다,2]",
            "[사이다-Y]", "[사이다-0]", "[사이다--1]"
    })
    void 잘못된_주문_입력(String input) {
        // when & then
        assertThatThrownBy(() -> new Orders(stockManager, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_INPUT_FORMAT.getMessage());
    }

    @Test
    void 구매_수량_한도_초과() {
        // given
        String input = "[사이다-1000]";

        // when & then
        assertThatThrownBy(() -> new Orders(stockManager, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.EXCEEDED_PURCHASE_LIMIT.getMessage());
    }

}