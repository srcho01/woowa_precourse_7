package store.view;

import store.domain.Order;
import store.domain.Orders;
import store.domain.Product;
import store.service.CalculatePrice;

import java.util.List;

public class OutputView {

    private final CalculatePrice calculatePrice;

    public OutputView() {
        this.calculatePrice = new CalculatePrice();
    }

    public void printStock(List<Product> stock) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");

        for (Product product : stock) {
            System.out.println(product.toString());
        }

        System.out.println();
    }

    public void printReceipt(Orders orders) {
        printPurchaseDetails(orders);
        printPromotionDetails(orders);
        printPrice(orders);
    }

    private void printPurchaseDetails(Orders orders) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t" + "수량\t" + "금액");

        for (Order order : orders.getOrders()) {
            List<String> goods = calculatePrice.purchaseDetails(order);
            System.out.print(goods.getFirst() + "\t\t");
            System.out.print(goods.get(1) + "\t\t");
            System.out.println(goods.getLast());
        }
    }

    private void printPromotionDetails(Orders orders) {
        System.out.println("=============증\t정===============");
        for (Order order : orders.getOrders()) {
            printPromotion(order);
        }
    }

    private void printPromotion(Order order) {
        List<String> promotionDetails = calculatePrice.promotionDetails(order);
        if (promotionDetails.size() == 2) {
            System.out.print(promotionDetails.getFirst() + "\t\t");
            System.out.println(promotionDetails.getLast());
        }
    }

    private void printPrice(Orders orders) {
        List<String> receiptDetail = calculatePrice.receiptDetails(orders);

        System.out.println("====================================");
        System.out.println("총구매액\t\t" + receiptDetail.getFirst() + "\t" +
                receiptDetail.get(1));
        System.out.println("행사할인\t\t\t-" + receiptDetail.get(2));
        System.out.println("멤버십할인\t\t\t-" + receiptDetail.get(3));
        System.out.println("내실돈\t\t\t" + receiptDetail.getLast());
    }

}
