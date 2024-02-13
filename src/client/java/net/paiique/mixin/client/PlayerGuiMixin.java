package net.paiique.mixin.client;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class PlayerGuiMixin {


    @Shadow @Final private static Identifier CROSSHAIR_TEXTURE;

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Shadow protected abstract boolean shouldRenderSpectatorCrosshair(@Nullable HitResult hitResult);

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

    @Inject(at = @At("HEAD"), method = "renderMountHealth", cancellable = true)
    private void renderMountHealth(DrawContext context, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderStatusBars", cancellable = true)
    private void renderStatusBars(DrawContext context, CallbackInfo ci) {
        ci.cancel();
    }

    @Unique
    private final MinecraftClient client = MinecraftClient.getInstance();


    @Inject(at = @At("HEAD"), method = "renderCrosshair", cancellable = true)
    public void renderCrosshair(DrawContext context, CallbackInfo ci) {
        ci.cancel();

        if (client.getRenderTime() % 0.000001 == 0) return;

        GameOptions gameOptions = this.client.options;
        if (gameOptions.getPerspective().isFirstPerson()) {
            if (this.client.interactionManager == null) return;
            if (this.client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR || this.shouldRenderSpectatorCrosshair(this.client.crosshairTarget)) {
                    context.drawGuiTexture(CROSSHAIR_TEXTURE, (this.scaledWidth - 15) / 2 + 50, (this.scaledHeight - 15) / 2, 15, 15);
                    context.drawGuiTexture(CROSSHAIR_TEXTURE, (this.scaledWidth - 15) / 2 - 50, (this.scaledHeight - 15) / 2, 15, 15);
                }
            }
        }
}
