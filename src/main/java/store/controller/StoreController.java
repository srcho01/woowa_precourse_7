package store.controller;

import store.domain.ItemFactory;
import store.domain.Product;
import store.domain.Promotion;
import store.view.OutputView;

import java.util.List;

public class StoreController {

    private List<Product> stock;
    private List<Promotion> promotions;

    private final ItemFactory itemFactory;
    private final OutputView outputView;

    public StoreController() {
        itemFactory = new ItemFactory();
        outputView = new OutputView();
    }

    public void run() {
        stock = itemFactory.stockFactory();
        promotions = itemFactory.promotionsFactory();

        outputView.printStock(stock);
    }

}
