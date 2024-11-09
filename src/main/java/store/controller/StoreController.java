package store.controller;

import store.domain.ItemFactory;
import store.domain.Product;
import store.domain.Promotion;

import java.util.List;

public class StoreController {

    private List<Product> stock;
    private List<Promotion> promotions;

    private final ItemFactory itemFactory;

    public StoreController() {
        itemFactory = new ItemFactory();
    }

    public void run() {
        stock = itemFactory.stockFactory();
        promotions = itemFactory.promotionsFactory();
    }

}
