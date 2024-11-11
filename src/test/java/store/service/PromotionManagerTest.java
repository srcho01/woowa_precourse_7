package store.service;

import org.junit.jupiter.api.Test;
import store.domain.Promotion;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionManagerTest {

    @Test
    void 이름으로_Promotion_객체_찾기() {
        // given
        String name = "탄산2+1";

        // when
        Promotion result = PromotionManager.getInstance().getPromotionByName(name);

        // then
        assertThat(result.getName()).isEqualTo("탄산2+1");
    }

    @Test
    void 이름으로_없는_Promotion_객체_찾기() {
        // given
        String name = "없는 프로모션";

        // when
        Promotion result = PromotionManager.getInstance().getPromotionByName(name);

        // then
        assertThat(result).isNull();
    }

}