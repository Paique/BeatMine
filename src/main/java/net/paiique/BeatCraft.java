package net.paiique;

import lombok.Data;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.crash.CrashReport;
import net.paiique.items.BlueSaberItem;
import net.paiique.items.ItemManager;
import net.paiique.items.RedSaberItem;
import net.paiique.items.SaberItem;
import net.paiique.player.PlayerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Data
public class BeatCraft implements ModInitializer {

    public static final String MOD_ID = "beatcraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private List<SaberItem> saberItems;
    public static PlayerData playerData;

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            CrashReport.create(new Throwable(), "BeatCraft is not supported on a server environment, not initializing mod.");
            return;
        }

        playerData = new PlayerData();
        new ItemManager().register();

    }

}