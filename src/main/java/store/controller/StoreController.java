package store.controller;

import store.domain.Order;
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
        boolean flag = false;

        do {
            printStock();
            Orders orders = doOrder();
            printReceipt(orders);
            flag = doNext(orders);
        } while (flag);
    }

    private void printStock() {
        outputView.printStock(StockManager.getInstance().getStock());
    }

    private Orders doOrder() {
        Orders orders = inputView.readOrder();
        adjustPromotion(orders);

        boolean membership = inputView.readMembership();
        orders.addMembership(membership);

        return orders;
    }

    private void printReceipt(Orders orders) {
        outputView.printReceipt(orders);
    }

    private boolean doNext(Orders orders) {
        orders.reduceInventory();
        return inputView.readBuyMore();
    }

    private void adjustPromotion(Orders orders) {
        for (Order order : orders.getOrders()) {
            int adjustAmount = order.adjustPromotion();
            if (adjustAmount > 0) {
                addPromotion(order, adjustAmount);
            }
            if (adjustAmount < 0) {
                removePromotion(order, adjustAmount);
            }
        }
    }

    private void addPromotion(Order order, int addAmount) {
        boolean isAdd = inputView.readAddPromotionAmount(order.getProduct().getName(), addAmount);
        if (isAdd) {
            order.addQuantity(addAmount);
        }
    }

    private void removePromotion(Order order, int removeAmount) {
        boolean fullPrice = inputView.readFullPrice(order.getProduct().getName(), -removeAmount);
        if (!fullPrice) {
            order.addQuantity(removeAmount);
        }
    }

}
