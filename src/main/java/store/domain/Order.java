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

    public void reduceInventory() {
        product.decreaseStock(quantity);
    }

    public int countPromotion() {
        return product.countPromotion(quantity);
    }

    public int adjustPromotion() {
        return product.adjustPromotion(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    private void validateProduct(String productName) {
        if (!StockManager.getInstance().contains(productName)) {
            throw ErrorCode.INVALID_INPUT.exception();
        }
    }

    private void validateQuantity(int purchaseAmount) {
        if (!product.canPurchase(purchaseAmount)) {
            throw ErrorCode.QUANTITY_EXCEED_STOCK.exception();
        }
    }

}
