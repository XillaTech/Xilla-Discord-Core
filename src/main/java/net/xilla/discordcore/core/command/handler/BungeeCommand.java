package net.xilla.discordcore.core.command.handler;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.CoreCommandExecutor;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.core.command.event.BungeeCommandEvent;

import java.util.Arrays;

public class BungeeCommand extends Command {

    public BungeeCommand() {
        super("discord");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        CommandData data;
        if (strings.length >= 2) {
            data = new CommandData<>(strings[0], Arrays.copyOfRange(strings, 1, strings.length), new BungeeCommandEvent(commandSender), CoreCommandExecutor.bungee_input, new BungeeUser(commandSender));
        } else if (strings.length == 1) {
            data = new CommandData<>(strings[0], new String[] {}, new BungeeCommandEvent(commandSender), CoreCommandExecutor.bungee_input, new BungeeUser(commandSender));
        } else {
            commandSender.sendMessage(new ComponentBuilder("That is not a valid command!").color(ChatColor.RED).create());
            return;
        }
        DiscordCore.getInstance().getCommandManager().runCommand(data);
    }

}
