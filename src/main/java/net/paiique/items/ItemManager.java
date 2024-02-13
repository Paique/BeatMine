package net.paiique.items;

import java.util.List;

public class ItemManager {

    public static List<SaberItem> saberItems;

    public ItemManager() {
        saberItems = List.of(
                new BlueSaberItem(),
                new RedSaberItem()
        );
    }

    public void register() {
        saberItems.forEach(SaberItem::register);
    }
}
