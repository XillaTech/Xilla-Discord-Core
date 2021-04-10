package net.xilla.discord.manager.command.discord.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.xilla.discord.api.command.CommandExecutor;
import net.xilla.discord.api.command.CommandInput;
import net.xilla.discord.manager.command.discord.DiscordCommand;

import java.util.Collection;
import java.util.Collections;

public class DiscordHelp extends DiscordCommand {

    public DiscordHelp() {
        super("Help", "Gives a list of all available commands", "perm", Collections.singletonList("help"), get());
    }

    private static CommandExecutor get() {
        return input -> {
            GuildMessageReceivedEvent event = input.getEvent();
            event.getChannel().sendMessage("Help Message").queue();
        };
    }

}
