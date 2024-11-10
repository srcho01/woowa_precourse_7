package store.domain;

import store.service.PromotionManager;

public class Product {

    private final String name;
    private final int price;
    private Promotion promotion;
    private int quantity;
    private int promotionQuantity;

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, int price, String promotionName, int promotionQuantity) {
        this.name = name;
        this.price = price;
        this.promotion = PromotionManager.getInstance().getPromotionByName(promotionName);
        this.promotionQuantity = promotionQuantity;
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    public int neededPromotionQuantity(int purchaseAmount) {
        if (!hasPromotion() || promotion.isExpired()) {
            return 0;
        }

        return purchaseAmount - neededRegularQuantity(purchaseAmount);
    }

    public int neededRegularQuantity(int purchaseAmount) {
        return promotion.calculateRegularQuantity(purchaseAmount);
    }

    public boolean canBuyRegular(int purchaseAmount) {
        return neededRegularQuantity(purchaseAmount) <= quantity;
    }

    public boolean canGetPromotion(int purchaseAmount) {
        return neededPromotionQuantity(purchaseAmount) <= promotionQuantity;
    }

    public boolean canBuyPromotionInRegular(int purchaseAmount) {
        int promotionCount = Math.min(promotion.calculateRegularQuantity(purchaseAmount), promotionQuantity);
        return purchaseAmount - promotionCount <= quantity;
    }

    public void addPromotion(String promotionName, int promotionQuantity) {
        this.promotion = PromotionManager.getInstance().getPromotionByName(promotionName);
        this.promotionQuantity = promotionQuantity;
    }

    public void addRegular(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        String promotionOutput = "";
        if (promotion != null) {
            promotionOutput = toStringPromotion() + "\n";
        }

        return promotionOutput + toStringRegular();
    }

    public String getName() {
        return name;
    }

    private String toStringPromotion() {
        return "- " + name + " " +
                formatPrice(price) +
                formatQuantity(promotionQuantity) +
                formatPromotion(promotion);
    }

    private String toStringRegular() {
        return "- " + name + " " +
                formatPrice(price) +
                formatQuantity(quantity);
    }

    private String formatPrice(int price) {
        return String.format("%,d", price) + "원 ";
    }

    private String formatQuantity(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.format("%,d", quantity) + "개 ";
    }

    private String formatPromotion(Promotion promotion) {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }
}
