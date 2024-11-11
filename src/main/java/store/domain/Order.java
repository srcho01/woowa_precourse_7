package store.domain;

import store.service.StockManager;
import store.util.ErrorCode;

public class Order {

    private final Product product;
    private int quantity;

    public Order(StockManager stockManager, String productName, int quantity) {
        validateProduct(stockManager, productName);
        this.product = stockManager.getProductByName(productName);

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

    public void addQuantity(int quantity) {
        int adjustQuantity = this.quantity + quantity;
        if (!product.canPurchase(adjustQuantity) || adjustQuantity < 0) {
            throw new InternalError(ErrorCode.INTERNAL_ERROR.getMessage());
        }

        this.quantity = adjustQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    private void validateProduct(StockManager stockManager, String productName) {
        if (!stockManager.contains(productName)) {
            throw ErrorCode.PRODUCT_NOT_FOUND.exception();
        }
    }

    private void validateQuantity(int purchaseAmount) {
        if (!product.canPurchase(purchaseAmount)) {
            throw ErrorCode.QUANTITY_EXCEED_STOCK.exception();
        }
    }

}
