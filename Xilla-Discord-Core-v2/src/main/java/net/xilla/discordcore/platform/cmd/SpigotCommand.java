package net.xilla.discordcore.platform.cmd;

import com.tobiassteely.tobiasapi.command.data.CommandData;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.event.SpigotCommandEvent;
import net.xilla.discordcore.command.permission.SpigotUser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SpigotCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandData data = new CommandData<>(new SpigotCommandEvent(sender, command), CoreCommandExecutor.spigot_input, new SpigotUser(sender));

        if (args.length >= 2) {
            DiscordCore.getInstance().getCommandManager().runCommand(args[0], Arrays.copyOfRange(args, 1, args.length - 1), data);
        } else  if (args.length == 1) {
            DiscordCore.getInstance().getCommandManager().runCommand(args[0], new String[] {}, data);
        } else {
            sender.sendMessage(ChatColor.RED + "That is not a valid command!");
        }
        return true;
    }

}