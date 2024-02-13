package net.paiique.player;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.paiique.BeatCraft;

import java.util.UUID;

@Setter
@Getter
public class ModPlayer {

    protected PlayerEntity player;

    protected World dim;

    protected float points;

    protected UUID uuid;

    public ModPlayer(ServerPlayerEntity player) {
        BeatCraft.LOGGER.info("Player " + player.getName().getString() + " has added to playerData");
        this.player = player;
        this.dim = player.getServerWorld();
        this.uuid = player.getUuid();
    }
}
