package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Orders;
import store.util.ErrorCode;

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

    public boolean readMembership() {
        while (true) {
            try {
                System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
                String input = Console.readLine();
                System.out.println();
                return parseYesNo(input);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage() + "\n");
            }
        }
    }

    private void validateYorN(String input) {
        if (!input.equals("y") && !input.equals("n")) {
            throw ErrorCode.INVALID_INPUT.exception();
        }
    }

    private boolean parseYesNo(String input) {
        String lowerInput = input.toLowerCase();

        validateYorN(lowerInput);
        return lowerInput.equals("y");
    }

}
