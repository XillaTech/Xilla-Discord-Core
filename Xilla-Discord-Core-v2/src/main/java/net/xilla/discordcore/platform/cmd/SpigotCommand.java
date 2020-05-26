package net.xilla.discordcore.platform.cmd;

import net.xilla.discordcore.DiscordCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpigotCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            StringBuilder message = new StringBuilder();
            for(String str :  args) {
                message.append(str);
            }
            DiscordCore.getInstance().getCommandManager().runGameCommand(message.toString());
        } else {
            sender.sendMessage(ChatColor.RED + "This command is console only.");
        }
        return true;
    }

}