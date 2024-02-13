package net.paiique.mixin.client;


import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin is used to remove the player's ability to walk.
 * @Author Paique
 */

@Mixin(GameOptions.class)
public class PlayerWalkMixin {

    @Inject(at = @At(value = "INVOKE", target = ""), method = "getClampedViewDistance", cancellable = true)
    public void getClampedViewDistance(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(20);
    }
}
