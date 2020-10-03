package net.xilla.discordcore.core.cmd;

import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.event.SpigotCommandEvent;
import net.xilla.discordcore.command.permission.SpigotUser;
import net.xilla.discordcore.core.command.CommandData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SpigotCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandData data;
        if (args.length >= 2) {
             data = new CommandData<>(args[0], Arrays.copyOfRange(args, 1, args.length), new SpigotCommandEvent(sender, command), CoreCommandExecutor.spigot_input, new SpigotUser(sender));
        } else  if (args.length == 1) {
            data = new CommandData<>(args[0], new String[] {}, new SpigotCommandEvent(sender, command), CoreCommandExecutor.spigot_input, new SpigotUser(sender));
        } else {
            sender.sendMessage(ChatColor.RED + "That is not a valid command!");
            return true;
        }
        DiscordCore.getInstance().getCommandManager().runCommand(data);
        return true;
    }

}