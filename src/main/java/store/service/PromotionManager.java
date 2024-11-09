package store.service;

import store.domain.ItemFactory;
import store.domain.Promotion;

import java.util.List;

public class PromotionManager {

    private static final PromotionManager instance = new PromotionManager();
    private final List<Promotion> promotions;

    private PromotionManager() {
        ItemFactory itemFactory = new ItemFactory();
        promotions = itemFactory.promotionsFactory();
    }

    public static PromotionManager getInstance() {
        return instance;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

}
