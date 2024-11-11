package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemFactoryTest {

    private ItemFactory itemFactory;

    @BeforeEach
    void setUp() {
         itemFactory = new ItemFactory();
    }

    @Test
    void 프로모션_팩토리_검증() {
        // given
        List<String> promotionData = List.of("탄산2+1,2,1,2024-01-01,2024-12-31");

        // when
        List<Promotion> promotions = itemFactory.promotionsFactory(promotionData);

        // then
        assertThat(promotions.size()).isEqualTo(1);
        Promotion promotion = promotions.getFirst();
        assertThat(promotion.getName()).isEqualTo("탄산2+1");
    }

    @Test
    void 상품_팩토리_검증() {
        // given
        List<String> productData = List.of(
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null"
        );

        // when
        List<Product> products = itemFactory.stockFactory(productData);

        // then
        assertThat(products.size()).isEqualTo(1);
        Product product = products.getFirst();
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
    }

}