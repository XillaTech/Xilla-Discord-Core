package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.command.CommandExecutor;
import com.tobiassteely.tobiasapi.command.data.CommandData;
import com.tobiassteely.tobiasapi.command.response.CommandResponse;
import net.xilla.discordcore.command.response.CoreCommandResponse;

public interface CoreCommandExecutor extends CommandExecutor {

    String discord_input = "discord";
    String spigot_input = "spigot";
    String bungee_input = "bungee";

    CoreCommandResponse run(String command, String[] args, CommandData data);

}
