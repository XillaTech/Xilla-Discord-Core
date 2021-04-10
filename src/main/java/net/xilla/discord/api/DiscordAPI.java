package net.xilla.discord.api;

import net.xilla.discord.api.command.CommandProcessor;
import net.xilla.discord.api.console.ConsoleProcessor;
import net.xilla.discord.api.permission.GroupProcessor;
import net.xilla.discord.api.permission.UserProcessor;

/**
 * Provides an interface to access all key data
 * access points.
 */
public interface DiscordAPI {

    /**
     * Returns the Command Processor
     *
     * @return Command Processor
     */
    CommandProcessor getCommandProcessor();

    /**
     * Returns the Console Processor
     *
     * @return Console Processor
     */
    ConsoleProcessor getConsoleProcessor();

    /**
     * Returns the Group Processor
     *
     * @return Group Processor
     */
    GroupProcessor getGroupProcessor();

    /**
     * Returns the User Processor
     *
     * @return User Processor
     */
    UserProcessor getUserProcessor();

}
