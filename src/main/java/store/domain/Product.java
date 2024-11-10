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
        this.promotionQuantity = 0;
    }

    public Product(String name, int price, String promotionName, int promotionQuantity) {
        this.name = name;
        this.price = price;
        this.promotion = PromotionManager.getInstance().getPromotionByName(promotionName);
        this.promotionQuantity = promotionQuantity;
        this.quantity = 0;
    }

    public void decreaseStock(int amount) {
        int usePromotion = 0;
        if (hasPromotion()) {
            usePromotion = Math.min(amount, promotionQuantity);
            promotionQuantity -= usePromotion;
        }

        quantity -= amount - usePromotion;
    }

    public boolean hasPromotion() {
        return promotion != null && !promotion.isExpired();
    }

    public boolean canPurchase(int purchaseAmount) {
        if (hasPromotion()) {
            return purchaseAmount <= quantity + promotionQuantity;
        }
        return purchaseAmount <= quantity;
    }

    public boolean unableGetPromotion(int purchaseAmount) {
        return purchaseAmount > promotionQuantity;
    }

    public int adjustPromotion(int purchaseAmount) {
        if (!hasPromotion()) {  // 프로모션이 없는 경우
            return 0;
        }

        // 프로모션 재고가 부족하여 정가로 결제해야 하는 경우 (음수로 표현)
        if (unableGetPromotion(purchaseAmount)) {
            return promotion.insufficientPromotion(purchaseAmount, promotionQuantity);
        }

        // 프로모션 재고를 추가해야 하는 경우 (양수로 표현)
        return Math.min(promotionQuantity - purchaseAmount, promotion.additionalPromotion(purchaseAmount));
    }

    public int countPromotion(int purchaseAmount) {
        if (hasPromotion()) {
            return promotion.countPromotion(purchaseAmount, promotionQuantity);
        }
        return 0;
    }

    public void addPromotion(String promotionName, int promotionQuantity) {
        this.promotion = PromotionManager.getInstance().getPromotionByName(promotionName);
        this.promotionQuantity = promotionQuantity;
    }

    public void addRegular(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String promotionOutput = "";
        if (promotion != null) {
            promotionOutput = toStringPromotion() + "\n";
        }

        return promotionOutput + toStringRegular();
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
            return "재고 없음 ";
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
