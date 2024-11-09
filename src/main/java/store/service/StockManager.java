package store.service;

import store.domain.ItemFactory;
import store.domain.Product;

import java.util.List;

public class StockManager {

    private static final StockManager instance = new StockManager();
    private final List<Product> stock;

    private StockManager() {
        ItemFactory itemFactory = new ItemFactory();
        stock = itemFactory.stockFactory();
    }

    public static StockManager getInstance() {
        return instance;
    }

    public List<Product> getStock() {
        return stock;
    }

    public Product getProductByName(String name) {
        for (Product product : stock) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

}
