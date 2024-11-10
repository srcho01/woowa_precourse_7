package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Orders;
import store.service.StockManager;
import store.util.ErrorCode;

public class InputView {

    public Orders readOrder(StockManager stockManager) {
        while (true) {
            try {
                System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
                String input = Console.readLine();
                System.out.println();
                return new Orders(stockManager, input);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage() + "\n");
            }
        }
    }

    public boolean readAddPromotionAmount(String name, int amount) {
        while (true) {
            try {
                System.out.println("현재 " + name + "은(는) " + amount + "개를 무료로 더 받을 수 있습니다. " +
                        "추가하시겠습니까? (Y/N)");
                String input = Console.readLine();
                System.out.println();
                return parseYesNo(input);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage() + "\n");
            }
        }
    }

    public boolean readFullPrice(String name, int amount) {
        while (true) {
            try {
                System.out.println("현재 " + name + " " + amount + "개는 프로모션 할인이 적용되지 않습니다. " +
                        "그래도 구매하시겠습니까? (Y/N)");
                String input = Console.readLine();
                System.out.println();
                return parseYesNo(input);
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

    public boolean readBuyMore() {
        while (true) {
            try {
                System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
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
