package net.xilla.discord.api.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.xilla.core.library.NotNull;
import net.xilla.core.library.Nullable;
import net.xilla.discord.api.permission.PermissionUser;

/**
 * The raw data provided from the discord API input to
 * process a command. Should have the provided permission
 * user and text input.
 */
public interface CommandInput {

    /**
     * Returns the user executing the command.
     *
     * @return PermissionUser Executor
     */
    @NotNull
    PermissionUser getExecutor();

    /**
     * Returns the raw users input
     *
     * @return Users Input
     */
    @NotNull
    String getRawInput();

    /**
     * Returns the attempted command name.
     *
     * @return Command Name
     */
    @NotNull
    String getCommand();

    /**
     * Input after the command split up.
     *
     * @return Command Arguments
     */
    @NotNull
    String[] getArgs();

    /**
     * Input after the command together.
     *
     * @return Command Arguments
     */
    @NotNull
    String getInput();

    /**
     * Returns the event if it was a discord command
     *
     * @return Message Received Event
     */
    @Nullable
    GuildMessageReceivedEvent getEvent();

}
