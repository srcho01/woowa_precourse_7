package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DataReaderTest {

    private DataReader dataReader;

    @BeforeEach
    void setUp() {
        dataReader = new DataReader();
    }

    @Test
    void 상품_정보_불러오기() {
        // when
        List<String> result = dataReader.productReader();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFirst()).isEqualTo("콜라,1000,10,탄산2+1");
        assertThat(result.get(1)).isEqualTo("콜라,1000,10,null");
    }

    @Test
    void 프로모션_정보_불러오기() {
        // when
        List<String> result = dataReader.promotionReader();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFirst()).isEqualTo("탄산2+1,2,1,2024-01-01,2024-12-31");
        assertThat(result.get(1)).isEqualTo("MD추천상품,1,1,2024-01-01,2024-12-31");
    }

}