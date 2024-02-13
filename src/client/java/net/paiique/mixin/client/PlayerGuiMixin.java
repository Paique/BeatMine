package net.paiique.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This mixin is used to remove the hotbar, health bar, experience bar, and status bars from the player's GUI.
 * It also adds a custom crosshair to the player's GUI.
 * @Author Paique
 */

@Mixin(InGameHud.class)
public abstract class PlayerGuiMixin {
    @Shadow
    @Final
    private static Identifier CROSSHAIR_TEXTURE;

    @Shadow
    private int scaledWidth;

    @Shadow
    private int scaledHeight;

    @Inject(at = @At("HEAD"), method = "renderHotbar", cancellable = true)
    private void renderHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderHealthBar", cancellable = true)
    private void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderExperienceBar", cancellable = true)
    private void renderExperienceBar(DrawContext context, int x, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderStatusBars", cancellable = true)
    private void renderStatusBars(DrawContext context, CallbackInfo ci) {
        ci.cancel();
    }

    @Unique
    private final MinecraftClient client = MinecraftClient.getInstance();


    @Unique
    private float leftCrosshair = 50;

    @Unique
    private float rightCrosshair = 50;

    @Inject(at = @At("HEAD"), method = "renderCrosshair", cancellable = true)
    public void renderCrosshair(DrawContext context, CallbackInfo ci) {
        ci.cancel();

        if (client.player == null) return;
        if (client.getLastFrameDuration() % 0.00001 == 0 || client.getCurrentFps() < 60) return;
        GameOptions gameOptions = this.client.options;

        if (gameOptions.getPerspective().isFirstPerson()) {
            if (this.client.interactionManager == null) return;
            if (this.client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) return;

            if (client.player.getMovementDirection().asRotation() == 180.0f) {
                if (rightCrosshair > 0) rightCrosshair -= 0.1f;
                if (leftCrosshair > 0) leftCrosshair -= 0.1f;

                context.drawGuiTexture(CROSSHAIR_TEXTURE, (this.scaledWidth - 15) / 2 + (int) leftCrosshair, (this.scaledHeight - 15) / 2, 15, 15);
                if (rightCrosshair < 50) context.drawGuiTexture(CROSSHAIR_TEXTURE, (this.scaledWidth - 15) / 2 + (int) (rightCrosshair * -1), (this.scaledHeight - 15) / 2, 15, 15);
                return;
            }

            if (leftCrosshair < 50) leftCrosshair += 0.1f;
            if (rightCrosshair < 50) rightCrosshair += 0.1f;

            context.drawGuiTexture(CROSSHAIR_TEXTURE, (this.scaledWidth - 15) / 2 + (int) leftCrosshair, (this.scaledHeight - 15) / 2, 15, 15);
            context.drawGuiTexture(CROSSHAIR_TEXTURE, (this.scaledWidth - 15) / 2 + (int) (rightCrosshair * -1), (this.scaledHeight - 15) / 2, 15, 15);

        }
    }
}
