package store.domain;

import store.service.DataReader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemFactory {

    private final DataReader dataReader;

    public ItemFactory() {
        this.dataReader = new DataReader();
    }

    public List<Promotion> promotionsFactory() {
        List<String> promotionData = dataReader.promotionReader();
        List<Promotion> promotions = new ArrayList<>();

        for (String promotionItem : promotionData) {
            Promotion promotion = promotionBuilder(promotionItem);
            promotions.add(promotion);
        }

        return promotions;
    }

    public List<Product> stockFactory() {
        List<String> productData = dataReader.productReader();
        List<Product> stock = new ArrayList<>();

        for (String productItem : productData) {
            Product product = productBuilder(productItem);
            stock.add(product);
        }

        return stock;
    }

    private Promotion promotionBuilder(String promotionItem) {
        String[] splittedData = promotionItem.split(",");

        String name = splittedData[0];
        int buy = Integer.parseInt(splittedData[1]);
        int get = Integer.parseInt(splittedData[2]);
        LocalDate startDate = LocalDate.parse(splittedData[3]);
        LocalDate endDate = LocalDate.parse(splittedData[4]);

        return new Promotion(name, buy, get, startDate, endDate);
    }

    private Product productBuilder(String productItem) {
        String[] splittedData = productItem.split(",");

        String name = splittedData[0];
        int price = Integer.parseInt(splittedData[1]);
        int quantity = Integer.parseInt(splittedData[2]);
        String promotion = getPromotionName(splittedData[3]);

        return new Product(name, price, quantity, promotion);
    }

    private String getPromotionName(String promotion) {
        if (promotion.equals("null")) {
            return null;
        }
        return promotion;
    }

}
