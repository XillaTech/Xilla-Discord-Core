package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.event.BungeeCommandEvent;
import net.xilla.discordcore.command.event.SpigotCommandEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CoreCommandExecutor extends CommandExecutor {

    String discord_input = "discord";
    String spigot_input = "spigot";
    String bungee_input = "bungee";

}
