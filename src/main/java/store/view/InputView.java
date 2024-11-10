package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Orders;

public class InputView {

    public Orders readOrder() {
        while (true) {
            try {
                System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
                String input = Console.readLine();
                System.out.println();
                return new Orders(input);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage() + "\n");
            }
        }

    }

}
