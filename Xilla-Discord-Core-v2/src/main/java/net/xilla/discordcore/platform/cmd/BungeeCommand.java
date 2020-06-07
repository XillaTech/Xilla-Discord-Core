package net.xilla.discordcore.platform.cmd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class BungeeCommand extends Command {

    public BungeeCommand() {
        super("discordcore");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender.getName().equalsIgnoreCase("CONSOLE")) {
            StringBuilder message = new StringBuilder();
            for(String str :  strings) {
                message.append(str);
            }
            commandSender.sendMessage(new ComponentBuilder("This command is currently disabled.").color(ChatColor.RED).create());
            //DiscordCore.getInstance().getCommandManager().runGameCommand(message.toString());
        } else {
            commandSender.sendMessage(new ComponentBuilder("This command is console only.").color(ChatColor.RED).create());
        }
    }

}
