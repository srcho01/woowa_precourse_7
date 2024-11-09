package store.controller;

import store.service.StockManager;
import store.view.OutputView;

public class StoreController {

    private final OutputView outputView;

    public StoreController() {
        outputView = new OutputView();
    }

    public void run() {
        outputView.printStock(StockManager.getInstance().getStock());
    }

}
