package net.paiique.items;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paiique.BeatCraft;

public class BlueSaberItem implements SaberItem {
    public static final Item BLUE_SABER = Registry.register(Registries.ITEM, new Identifier(BeatCraft.MOD_ID, "blue_saber"), new SwordItem(ToolMaterials.DIAMOND, 0, 0, new Item.Settings()));

    @Override
    public void register() {
        BeatCraft.LOGGER.info("Registering blue saber item.");
    }
}
