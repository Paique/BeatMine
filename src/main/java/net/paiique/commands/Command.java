package net.paiique.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public interface Command {
    void register(CommandDispatcher<ServerCommandSource> dispatcher);
}
