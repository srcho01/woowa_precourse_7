package store.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    Product productBuilder(String name, int price, int regularQuantity,
                           String promotionName, int promotionQuantity) {
        if (promotionName == null || promotionName.isEmpty() || promotionName.equals("null")) {
            return new Product(name, price, regularQuantity);
        }

        Product product = new Product(name, price, promotionName, promotionQuantity);
        product.addRegular(regularQuantity);
        return product;
    }

    @ParameterizedTest
    @CsvSource(value = {
            "콜라; 1000; 10; 탄산2+1; 10; - 콜라 1,000원 10개 탄산2+1- 콜라 1,000원 10개",
            "콜라; 1000; 10; null; 0; - 콜라 1,000원 10개",
            "콜라; 1000; 0; 탄산2+1; 10; - 콜라 1,000원 10개 탄산2+1- 콜라 1,000원 재고 없음"
    }, delimiter = ';')
    void toString_검사(String name, int price, int regularQuantity,
                     String promotionName, int promotionQuantity, String expected) {
        // given
        Product product = productBuilder(name, price, regularQuantity, promotionName, promotionQuantity);

        // when
        String result = product.toString()
                .replace("\n", "").strip();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "콜라; 1000; 10; 탄산2+1; 10; 5; - 콜라 1,000원 5개 탄산2+1- 콜라 1,000원 10개",
            "콜라; 1000; 10; 탄산2+1; 10; 15; - 콜라 1,000원 재고 없음 탄산2+1- 콜라 1,000원 5개",
            "에너지바; 1000; 5; null; 0; 3; - 에너지바 1,000원 2개"
    }, delimiter = ';')
    void 재고_변경(String name, int price, int regularQuantity, String promotionName, int promotionQuantity,
               int purchase, String expected) {
        // given
        Product product = productBuilder(name, price, regularQuantity, promotionName, promotionQuantity);

        // when
        product.decreaseStock(purchase);
        String result = product.toString()
                .replace("\n", "").strip();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "에너지바; 1000; 5; null; 0; false",
            "콜라; 1000; 10; 지난할인; 10; false",
            "콜라; 1000; 10; 탄산2+1; 10; true",
    }, delimiter = ';')
    void 유효한_프로모션인지_확인(String name, int price, int regularQuantity, String promotionName, int promotionQuantity,
               boolean expected) {
        // given
        Product product = productBuilder(name, price, regularQuantity, promotionName, promotionQuantity);

        // when
        boolean result = product.hasPromotion();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "콜라; 1000; 10; 탄산2+1; 10; 15; true",
            "콜라; 1000; 10; 지난할인; 10; 15; false",
            "에너지바; 1000; 5; null; 0; 4; true",
            "에너지바; 1000; 5; null; 0; 8; false",
    }, delimiter = ';')
    void 구매가_가능한지_확인(String name, int price, int regularQuantity, String promotionName, int promotionQuantity,
                       int purchase, boolean expected) {
        // given
        Product product = productBuilder(name, price, regularQuantity, promotionName, promotionQuantity);

        // when
        boolean result = product.canPurchase(purchase);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "콜라; 1000; 10; 탄산2+1; 10; 15; true",
            "콜라; 1000; 10; 탄산2+1; 10; 6; false",
    }, delimiter = ';')
    void 프로모션_재고가_부족한지_확인(String name, int price, int regularQuantity, String promotionName, int promotionQuantity,
                     int purchase, boolean expected) {
        // given
        Product product = productBuilder(name, price, regularQuantity, promotionName, promotionQuantity);

        // when
        boolean result = product.unableGetPromotion(purchase);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "에너지바; 1000; 5; null; 0; 3; 0",
            "콜라; 1000; 10; 탄산2+1; 7; 10; -4",
            "콜라; 1000; 10; 탄산2+1; 10; 3; 0",
            "콜라; 1000; 10; 탄산2+1; 10; 5; 1",
            "콜라; 1000; 10; 지난할인; 10; 5; 0",
            "주스; 1000; 10; MD추천상품; 9; 9; 0"
    }, delimiter = ';')
    void 프로모션_개수_조정(String name, int price, int regularQuantity, String promotionName, int promotionQuantity,
                          int purchase, int expected) {
        // given
        Product product = productBuilder(name, price, regularQuantity, promotionName, promotionQuantity);

        // when
        int result = product.adjustPromotion(purchase);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "에너지바; 1000; 5; null; 0; 3; 0",
            "콜라; 1000; 10; 지난할인; 10; 9; 0",
    }, delimiter = ';')
    void 프로모션_개수_세기(String name, int price, int regularQuantity, String promotionName, int promotionQuantity,
                    int purchase, int expected) {
        // given
        Product product = productBuilder(name, price, regularQuantity, promotionName, promotionQuantity);

        // when
        int result = product.countPromotion(purchase);

        // then
        assertThat(result).isEqualTo(expected);
    }

}