package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDateTime;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String name, int buy, int get,
                     LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int countPromotion(int purchaseAmount, int promotionQuantity) {
        int promotionRegularSet = buy + get;
        return Math.min(purchaseAmount / promotionRegularSet, promotionQuantity / promotionRegularSet);
    }

    public int additionalPromotion(int purchaseAmount, int promotionQuantity) {
        int promotionRegularSet = buy + get;
        if ((purchaseAmount - promotionRegularSet * (purchaseAmount / promotionRegularSet)) == buy) {
            return Math.min(get, promotionQuantity);
        }
        return 0;
    }

    public int insufficientPromotion(int purchaseAmount, int promotionQuantity) {
        int promotionRegularSet = buy + get;
        int availablePromotion = promotionRegularSet * (promotionQuantity / promotionRegularSet);
        return Math.min(availablePromotion - purchaseAmount, 0);
    }

    public boolean isExpired() {
        LocalDateTime now = DateTimes.now();
        return now.isBefore(startDate) || now.isAfter(endDate);
    }

}
