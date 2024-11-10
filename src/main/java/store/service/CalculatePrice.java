package store.service;

import store.domain.Order;
import store.domain.Orders;
import store.util.Constant;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculatePrice {

    public List<String> purchaseDetails(Order order) {
        List<String> goods = new ArrayList<>();
        int quantity = order.getQuantity();
        int price = order.getProduct().getPrice();

        goods.add(order.getProduct().getName());
        goods.add(numberFormatter(quantity));
        goods.add(numberFormatter(quantity * price));

        return goods;
    }

    public List<String> promotionDetails(Order order) {
        int countedPromotion = order.countPromotion();
        if (countedPromotion > 0) {
            return List.of(order.getProduct().getName(), numberFormatter(countedPromotion));
        }
        return List.of();
    }

    public List<String> receiptDetails(Orders orders) {
        return List.of(
                numberFormatter(getTotalAmount(orders)),
                numberFormatter(getTotalPrice(orders)),
                numberFormatter(getPromotionPrice(orders)),
                numberFormatter(getMembershipPrice(orders)),
                numberFormatter(moneyToPay(orders))
        );
    }

    private int getTotalAmount(Orders orders) {
        int total = 0;
        for (Order order : orders.getOrders()) {
            total += order.getQuantity();
        }

        return total;
    }

    private int getTotalPrice(Orders orders) {
        int total = 0;
        for (Order order : orders.getOrders()) {
            total += calculateOrderPrice(order);
        }

        return total;
    }

    private int calculateOrderPrice(Order order) {
        return order.getQuantity() * order.getProduct().getPrice();
    }

    private int getPromotionPrice(Orders orders) {
        int total = 0;
        for (Order order : orders.getOrders()) {
            total += order.countPromotion() * order.getProduct().getPrice();
        }

        return total;
    }

    private int getMembershipPrice(Orders orders) {
        if (orders.isMembership()) {
            int discounted = calculateMembership(orders);
            return Math.min(discounted, Constant.MAX_MEMBERSHIP_DISCOUNT);
        }

        return 0;
    }

    private int calculateMembership(Orders orders) {
        int total = 0;

        for (Order order : orders.getOrders()) {
            if (!order.getProduct().hasPromotion()) {
                total += roundToTens(calculateOrderPrice(order) * 0.3);
            }
        }

        return total;
    }

    public int moneyToPay(Orders orders) {
        return getTotalPrice(orders) - getPromotionPrice(orders) - getMembershipPrice(orders);
    }

    private int roundToTens(double value) {
        return (int) (Math.floor(value / 10) * 10);
    }

    private String numberFormatter(int number) {
        NumberFormat formatter = NumberFormat.getInstance();
        return formatter.format(number);
    }

}
