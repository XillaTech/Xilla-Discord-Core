package net.xilla.discord.manager.command.discord.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.xilla.core.library.Pair;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.api.command.CommandExecutor;
import net.xilla.discord.api.command.CommandInput;
import net.xilla.discord.api.permission.PermissionUser;
import net.xilla.discord.manager.command.discord.DiscordCommand;
import net.xilla.discord.manager.command.discord.executors.SectionSelector;
import net.xilla.discord.manager.command.discord.executors.SendEmbed;
import net.xilla.discord.manager.command.discord.executors.SendMessage;
import net.xilla.discord.setting.DiscordSettings;

import java.util.Collection;
import java.util.Collections;

public class DiscordHelp extends DiscordCommand {

    public DiscordHelp() {
        super("Help", "Gives a list of all available commands", "core.help", Collections.singletonList("help"), get());
    }

    private static CommandExecutor get() {
        return (input) -> {
            SectionSelector selector = new SectionSelector("Help", 0);

            DiscordCore.getInstance().getCommandProcessor().listObjects().forEach(command -> {

                if(input.getExecutor().hasPermission(command.getPermission())) {

                    String description = command.getDescription();
                    if (description == null || description.isEmpty()) {
                        description = "No description provided";
                    }

                    selector.put(command.getName(), description, new SendMessage(description));
                }
            });

            selector.run(input);
        };
    }

}
