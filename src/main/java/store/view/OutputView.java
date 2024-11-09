package store.view;

import store.domain.Product;

import java.util.List;

public class OutputView {

    public void printStock(List<Product> stock) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");

        for (Product product : stock) {
            System.out.println(product.toString());
        }
    }

}
