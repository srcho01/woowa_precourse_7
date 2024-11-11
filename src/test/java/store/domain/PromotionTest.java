package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionTest {

    private Promotion colaPromotion;
    private Promotion orangeJuicePromotion;

    private final int colaPromotionQuantity = 10;
    private final int orangeJuicePromotionQuantity = 9;

    @BeforeEach
    void setUp() {
        colaPromotion = new Promotion("콜라", 2, 1,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59));

        orangeJuicePromotion = new Promotion("오렌지주스", 1, 1,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59));
    }

    @ParameterizedTest
    @CsvSource({ "15,3", "6,2" })
    void 프로모션_2plus1_개수_세기(int purchaseAmount, int expected) {
        // when
        int result = colaPromotion.countPromotion(purchaseAmount, colaPromotionQuantity);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void 프로모션_1plus1_개수_세기() {
        // when
        int result = orangeJuicePromotion.countPromotion(3, orangeJuicePromotionQuantity);

        // then
        assertThat(result).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource({ "4,0", "3,1", "9,0" })
    void 추가할_프로모션_개수(int purchaseAmount, int expected) {
        // when
        int result = orangeJuicePromotion.additionalPromotion(purchaseAmount, orangeJuicePromotionQuantity);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({ "15,-6", "3,0" })
    void 부족한_프로모션_개수(int purchaseAmount, int expected) {
        // when
        int result = colaPromotion.insufficientPromotion(purchaseAmount, colaPromotionQuantity);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({ "2023,true", "2024,false", "2025,true" })
    void 프로모션_만료_확인(int year, boolean expected) {
        // given
        Promotion promotion = new Promotion("초코우유", 2, 1,
                LocalDateTime.of(year, 1, 1, 0, 0),
                LocalDateTime.of(year, 12, 31, 23, 59));

        // when
        boolean result = promotion.isExpired();

        // then
        assertThat(result).isEqualTo(expected);
    }

}