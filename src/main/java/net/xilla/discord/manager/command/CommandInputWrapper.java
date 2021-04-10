package net.xilla.discord.manager.command;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.xilla.core.library.NotNull;
import net.xilla.core.library.Nullable;
import net.xilla.discord.api.command.CommandInput;
import net.xilla.discord.api.permission.PermissionUser;

/**
 * A simple wrapper for the CommandInput interface
 */
public class CommandInputWrapper implements CommandInput {

    @Getter
    @Setter
    @NotNull
    private PermissionUser executor;

    @Getter
    @Setter
    @NotNull
    private String rawInput;

    @Getter
    @Setter
    @NotNull
    private String command;

    @Getter
    @Setter
    @NotNull
    private String[] args;

    @Getter
    @Setter
    @NotNull
    private String input;

    @Getter
    @Setter
    @Nullable
    private GuildMessageReceivedEvent event = null;

    public CommandInputWrapper() {}

}
