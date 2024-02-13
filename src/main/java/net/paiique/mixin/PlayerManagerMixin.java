package net.paiique.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.paiique.BeatCraft;
import net.paiique.player.ModPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Inject(at = @At("HEAD"), method = "onPlayerConnect")
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {

        for(ModPlayer modPlayer : BeatCraft.playerData.players) {
            if (modPlayer.getUuid() == player.getUuid()) {
                BeatCraft.LOGGER.info("Player " + player.getName().getString() + " already has its added to playerData");
                return;
            }
        }

        new ModPlayer(player);

    }

}