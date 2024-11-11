package store.service;

import store.domain.ItemFactory;
import store.domain.Promotion;

import java.util.List;

public class PromotionManager {

    private static final PromotionManager instance = new PromotionManager();
    private final List<Promotion> promotions;

    private PromotionManager() {
        ItemFactory itemFactory = new ItemFactory();
        DataReader dataReader = new DataReader();

        List<String> promotionData = dataReader.promotionReader();
        promotions = itemFactory.promotionsFactory(promotionData);
    }

    public static PromotionManager getInstance() {
        return instance;
    }

    public Promotion getPromotionByName(String name) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) {
                return promotion;
            }
        }
        return null;
    }

}
