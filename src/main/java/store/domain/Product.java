package store.domain;

public class Product {

    private final String name;
    private final int price;
    private final int quantity;
    private final String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        String formattedPrice = String.format("%,d", price);
        String formattedQuantity = "재고 없음";
        if (quantity > 0) {
            formattedQuantity = String.format("%,d", quantity);
        }
        String printPromotion = "";
        if (promotion != null) {
            printPromotion = promotion;
        }

        return "- " + name + " " +
                formatPrice(price) + "원 " +
                formatQuantity(quantity) + "개 " +
                formatPromotion(promotion);
    }

    private String formatPrice(int price) {
        return String.format("%,d", price);
    }

    private String formatQuantity(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.format("%,d", quantity);
    }

    private String formatPromotion(String promotion) {
        if (promotion == null) {
            return "";
        }
        return promotion;
    }
}
