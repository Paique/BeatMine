package net.paiique.mixin.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.registry.Registry;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.path.SymlinkValidationException;
import net.royawesome.jlibnoise.module.combiner.Min;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Mixin(TitleScreen.class)
public class OnClientStartup {

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(CallbackInfo ci) throws IOException, URISyntaxException {
        ci.cancel();
        Path path = null;
        Path pathSaves = MinecraftClient.getInstance().getLevelStorage().getSavesDirectory().resolve("beatworld");

        File dir = pathSaves.toFile();

        URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource("beatworld")).toURI();
        if ("jar".equals(uri.getScheme())) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            path = fileSystem.getPath("beatworld");
        } else {
            path = Path.of(uri);
        }

        Files.copy(path, MinecraftClient.getInstance().getLevelStorage().getSavesDirectory());


        MinecraftClient.getInstance().createIntegratedServerLoader().start("beatworld", () -> {
            CrashReport.create(new IOException("Failed to start integrated server"), "Starting integrated server");
            throw new RuntimeException("Failed to start integrated server");
        });
    }

}
