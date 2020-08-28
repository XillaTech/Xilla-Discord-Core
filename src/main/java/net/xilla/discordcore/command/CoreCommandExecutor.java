package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.command.CommandData;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.xilla.discordcore.command.response.CoreCommandResponse;

public interface CoreCommandExecutor extends CommandExecutor {

    String discord_input = "discord";
    String spigot_input = "spigot";
    String bungee_input = "bungee";

    CoreCommandResponse run(CommandData data);

}
