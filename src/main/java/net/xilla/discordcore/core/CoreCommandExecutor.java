package net.xilla.discordcore.core;

import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.core.command.CommandExecutor;

public interface CoreCommandExecutor extends CommandExecutor {

    String discord_input = "discord";
    String spigot_input = "spigot";
    String bungee_input = "bungee";

    CoreCommandResponse run(CommandData data);

}
