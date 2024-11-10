package store.domain;

import store.service.DataReader;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            addProduct(stock, productItem);
        }

        return stock;
    }

    private Promotion promotionBuilder(String promotionItem) {
        String[] splittedData = promotionItem.split(",");

        String name = splittedData[0];
        int buy = Integer.parseInt(splittedData[1]);
        int get = Integer.parseInt(splittedData[2]);
        LocalDateTime startDate = LocalDate.parse(splittedData[3]).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(splittedData[4]).atStartOfDay();

        return new Promotion(name, buy, get, startDate, endDate);
    }

    private void addProduct(List<Product> stock, String productItem) {
        String[] splittedData = productItem.split(",");

        String name = splittedData[0];
        int price = Integer.parseInt(splittedData[1]);
        int quantity = Integer.parseInt(splittedData[2]);
        String promotionName = getPromotionName(splittedData[3]);

        addOrUpdateProductStock(stock, name, price, quantity, promotionName);
    }

    private void addOrUpdateProductStock(List<Product> stock,
                                         String name, int price, int quantity, String promotionName) {
        Product product = existProduct(stock, name);
        if (product == null) {
            stock.add(productBuilder(name, price, quantity, promotionName));
            return;
        }
        addQuantityToProduct(product, promotionName, quantity);
    }

    private void addQuantityToProduct(Product product, String promotionName, int quantity) {
        if (promotionName == null) {
            product.addRegular(quantity);
            return;
        }
        product.addPromotion(promotionName, quantity);
    }

    private Product productBuilder(String name, int price, int quantity, String promotionName) {
        if (promotionName == null) {
            return new Product(name, price, quantity);
        }
        return new Product(name, price, promotionName, quantity);
    }

    private Product existProduct(List<Product> stock, String name) {
        for (Product product : stock) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    private String getPromotionName(String promotion) {
        if (promotion.equals("null")) {
            return null;
        }
        return promotion;
    }

}
