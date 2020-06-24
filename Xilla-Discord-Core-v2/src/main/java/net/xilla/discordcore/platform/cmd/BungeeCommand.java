package net.xilla.discordcore.platform.cmd;

import com.tobiassteely.tobiasapi.command.data.CommandData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.event.BungeeCommandEvent;
import net.xilla.discordcore.command.permission.BungeeUser;

import java.util.Arrays;

public class BungeeCommand extends Command {

    public BungeeCommand() {
        super("discord");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        CommandData data = new CommandData<>(new BungeeCommandEvent(commandSender), CoreCommandExecutor.bungee_input, new BungeeUser(commandSender));

        DiscordCore.getInstance().getLog().sendMessage(0, "Bungee " + Arrays.deepToString(strings));

        if (strings.length >= 2) {
            DiscordCore.getInstance().getCommandManager().runCommand(strings[0], Arrays.copyOfRange(strings, 1, strings.length), data);
        } else  if (strings.length == 1) {
            DiscordCore.getInstance().getCommandManager().runCommand(strings[0], new String[] {}, data);
        } else {
            commandSender.sendMessage(new ComponentBuilder("That is not a valid command!").color(ChatColor.RED).create());
        }
    }

}
