package store.domain;

import store.util.Constant;
import store.util.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class Orders {

    private final List<Order> orders;
    private boolean membership;

    public Orders(String data) {
        validate(data);
        this.orders = new ArrayList<>();
        addOrders(data);
    }

    public void addMembership(boolean membership) {
        this.membership = membership;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void reduceInventory() {
        for (Order order : orders) {
            order.reduceInventory();
        }
    }

    private void addOrders(String data) {
        for (String item : data.split(",")) {
            String[] splitted = splitByDash(item);

            String productName = splitted[0];
            int quantity = Integer.parseInt(splitted[1]);

            this.orders.add(new Order(productName, quantity));
        }
    }

    private void validate(String data) {
        String[] splitByComma = data.split(",");
        for (String item : splitByComma) {
            isBracketed(item);
            checkProductAndQuantity(splitByDash(item));
        }
    }

    private void isBracketed(String item) {
        if (!item.startsWith("[") || !item.endsWith("]")) {
            throw ErrorCode.INVALID_INPUT_FORMAT.exception();
        }
    }

    private void checkProductAndQuantity(String[] data) {
        if (data.length != 2 || !data[1].matches(Constant.NATURAL_NUMBER_REGEX)) {
            throw ErrorCode.INVALID_INPUT_FORMAT.exception();
        }
    }

    private String[] splitByDash(String item) {
        String trimmed = item.substring(1, item.length() - 1);
        return trimmed.split("-");
    }

}
