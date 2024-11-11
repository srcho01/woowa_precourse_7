package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;

class StockManagerTest {

    private StockManager stockManager;

    @BeforeEach
    void setUp() {
        stockManager = new StockManager();
    }

    @Test
    void 이름으로_Product_객체_찾기() {
        // given
        String name = "콜라";

        // when
        Product result = stockManager.getProductByName(name);

        // then
        assertThat(result.getName()).isEqualTo("콜라");
        assertThat(result.getPrice()).isEqualTo(1000);
    }

    @Test
    void 이름으로_없는_Product_객체_찾기() {
        // given
        String name = "없는상품";

        // when
        Product result = stockManager.getProductByName(name);

        // then
        assertThat(result).isNull();
    }

}