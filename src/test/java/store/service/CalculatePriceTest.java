package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.Order;
import store.domain.Orders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatePriceTest {

    private CalculatePrice calculatePrice;
    private StockManager stockManager;

    @BeforeEach
    void setUp() {
        calculatePrice = new CalculatePrice();
        stockManager = new StockManager();
    }

    @Test
    void 구매_내역_테스트() {
        // given
        Order order = new Order(stockManager, "콜라", 10);

        // when
        List<String> result = calculatePrice.purchaseDetails(order);

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.getFirst()).isEqualTo("콜라");
        assertThat(result.get(1)).isEqualTo("10");
        assertThat(result.getLast()).isEqualTo("10,000");
    }

    @ParameterizedTest
    @CsvSource({ "콜라,20,3", "콜라,6,2", "오렌지주스,4,2" })
    void 증정_내역_테스트(String productName, int purchase, String expected) {
        // given
        Order order = new Order(stockManager, productName, purchase);

        // when
        List<String> result = calculatePrice.promotionDetails(order);

        // then
        assertThat(result.getFirst()).isEqualTo(productName);
        assertThat(result.getLast()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({ "에너지바,4", "할인끝난상품,5" })
    void 증정_내역_없음(String productName, int purchase) {
        // given
        Order order = new Order(stockManager, productName, purchase);

        // when
        List<String> result = calculatePrice.promotionDetails(order);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 총구매_수량() {
        // given
        String input = "[콜라-3],[에너지바-5],[오렌지주스-1]";
        Orders orders = new Orders(stockManager, input);

        // when
        List<String> result = calculatePrice.receiptDetails(orders);

        // then
        assertThat(result.getFirst()).isEqualTo("9");
    }

    @Test
    void 총구매액() {
        // given
        String input = "[콜라-3],[에너지바-5],[오렌지주스-1]";
        Orders orders = new Orders(stockManager, input);

        // when
        List<String> result = calculatePrice.receiptDetails(orders);

        // then
        assertThat(result.get(1)).isEqualTo("14,800");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "[콜라-3],[에너지바-5] ; 1,000",
            "[콜라-15] ; 3,000",
            "[에너지바-4] ; 0",
            "[할인끝난상품-3] ; 0"
    }, delimiter = ';')
    void 행사가격(String input, String promotionPrice) {
        // given
        Orders orders = new Orders(stockManager, input);

        // when
        List<String> result = calculatePrice.receiptDetails(orders);

        // then
        assertThat(result.get(2)).isEqualTo(promotionPrice);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "[콜라-3],[에너지바-5] ; 3,000",
            "[콜라-15] ; 0",
            "[에너지바-4] ; 2,400",
            "[할인끝난상품-3] ; 900"
    }, delimiter = ';')
    void 멤버십할인(String input, String membershipPrice) {
        // given
        Orders orders = new Orders(stockManager, input);
        orders.addMembership(true);

        // when
        List<String> result = calculatePrice.receiptDetails(orders);

        // then
        assertThat(result.get(3)).isEqualTo(membershipPrice);
    }

    @Test
    void 멤버십할인_원하지_않는_경우() {
        // given
        Orders orders = new Orders(stockManager, "[콜라-3],[에너지바-5]");
        orders.addMembership(false);

        // when
        List<String> result = calculatePrice.receiptDetails(orders);

        // then
        assertThat(result.get(3)).isEqualTo("0");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "[콜라-3],[에너지바-5] ; true ; 9,000",
            "[콜라-15] ; true ; 12,000",
            "[에너지바-4] ; true ; 5,600",
            "[할인끝난상품-3] ; true ; 2,100",
            "[콜라-15],[에너지바-5] ; false ; 22,000",
    }, delimiter = ';')
    void 내실돈(String input, boolean membership, String moneyToPay) {
        // given
        Orders orders = new Orders(stockManager, input);
        orders.addMembership(membership);

        // when
        List<String> result = calculatePrice.receiptDetails(orders);

        // then
        assertThat(result.get(4)).isEqualTo(moneyToPay);
    }

}