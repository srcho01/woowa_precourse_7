package store.controller;

import store.domain.Orders;
import store.service.StockManager;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final OutputView outputView;
    private final InputView inputView;

    public StoreController() {
        outputView = new OutputView();
        inputView = new InputView();
    }

    public void run() {
        outputView.printStock(StockManager.getInstance().getStock());

        Orders orders = inputView.readOrder();
        boolean membership = inputView.readMembership();
        orders.addMembership(membership);
    }

}
