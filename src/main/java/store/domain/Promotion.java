package store.domain;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get,
                     LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int calculateRegularQuantity(int purchaseAmount) {
        return (purchaseAmount / (buy + get)) * buy + (purchaseAmount % (buy + get));
    }

    public int additionalPromotion(int purchaseAmount) {
        int promotionRegularSet = buy + get;
        if ((purchaseAmount - promotionRegularSet * (purchaseAmount / promotionRegularSet)) % buy == 0) {
            return get;
        }
        return 0;
    }

    public int insufficientPromotion(int purchaseAmount, int promotionQuantity) {
        int promotionRegularSet = buy + get;
        int availablePromotion = promotionRegularSet * (promotionQuantity / promotionRegularSet);
        return Math.min(availablePromotion - purchaseAmount, 0);
    }

    public boolean isExpired() {
        LocalDate now = LocalDate.now();
        return now.isBefore(startDate) || now.isAfter(endDate);
    }

}
