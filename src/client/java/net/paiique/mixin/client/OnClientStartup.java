package net.paiique.mixin.client;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.crash.CrashReport;
import net.paiique.helper.CopyDir;
import net.paiique.helper.RemoveFolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Objects;

/**
 * This mixin is used to skip the title screen and start the game with a custom world.
 * @Author Paique
 */

@Mixin(TitleScreen.class)
public class OnClientStartup {

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(CallbackInfo ci) throws IOException, URISyntaxException {
        ci.cancel();
        Path path;
        Path pathSaves = MinecraftClient.getInstance().getLevelStorage().getSavesDirectory().resolve("beatworld");

        RemoveFolder.remove(pathSaves);

        URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("beatworld")).toURI();
        if ("jar".equals(uri.getScheme())) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            path = fileSystem.getPath("beatworld");
        } else {
            path = Path.of(uri);
        }

        CopyDir.copy(path, pathSaves);
        MinecraftClient.getInstance().createIntegratedServerLoader().start("beatworld", () -> {
            CrashReport.create(new IOException("Failed to start integrated server"), "Starting integrated server");
            throw new RuntimeException("Failed to start integrated server");
        });
    }

}
