package net.xilla.discord.api.command;

import net.xilla.core.library.NotNull;
import net.xilla.core.library.Nullable;

import java.util.List;

/**
 * Basic structure of all commands within this program
 */
public interface Command {

    /**
     * Returns the command's name.
     *
     * @return Command Name
     */
    @NotNull
    String getName();

    /**
     * Returns the command's description.
     *
     * @return Command Description
     */
    @Nullable
    String getDescription();

    /**
     * Returns the required permission if there is one,
     * if there is not one it will return null.
     *
     * @return Permission
     */
    @Nullable
    String getPermission();

    /**
     * Returns the list of command activators.
     *
     * @return Command Activators
     */
    @NotNull
    List<String> getActivators();

    /**
     * Returns the commands executor, used to run the command.
     *
     * @return Command Executor
     */
    @NotNull
    CommandExecutor getExecutor();

}
