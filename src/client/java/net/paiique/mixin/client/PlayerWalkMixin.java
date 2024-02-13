package net.paiique.mixin.client;


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

@Mixin(PlayerAbilities.class)
public class PlayerWalkMixin {

    @Inject(at = @At("HEAD"), method = "getWalkSpeed", cancellable = true)
    public void getWalkSpeed(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(0.0F);
    }
}
