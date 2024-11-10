package store.domain;

import store.service.StockManager;
import store.util.ErrorCode;

public class Order {

    private final Product product;
    private int quantity;

    public Order(String productName, int quantity) {
        validateProduct(productName);
        this.product = StockManager.getInstance().getProductByName(productName);

        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateProduct(String productName) {
        if (!StockManager.getInstance().contains(productName)) {
            throw ErrorCode.INVALID_INPUT.exception();
        }
    }

    private void validateQuantity(int purchaseAmount) {
        // 일반 재고가 부족한 경우
        if (!product.canBuyRegular(purchaseAmount)) {
            throw ErrorCode.QUANTITY_EXCEED_STOCK.exception();
        }

        // 프로모션 재고 부족을 일반 재고로도 살 수 없는 경우
        if (!product.canGetPromotion(purchaseAmount) && !product.canBuyPromotionInRegular(purchaseAmount)) {
            throw ErrorCode.QUANTITY_EXCEED_STOCK.exception();
        }
    }

}
