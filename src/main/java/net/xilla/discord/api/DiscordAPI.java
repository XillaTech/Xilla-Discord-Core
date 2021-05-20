package net.xilla.discord.api;

import net.xilla.discord.api.command.CommandProcessor;
import net.xilla.discord.api.command.console.ConsoleProcessor;
import net.xilla.discord.api.embed.EmbedProcessor;
import net.xilla.discord.api.permission.GroupProcessor;
import net.xilla.discord.api.permission.UserProcessor;
import net.xilla.discord.api.placeholder.PlaceholderProcessor;

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

    /**
     * Returns the Placeholder Processor
     *
     * @return Placeholder Processor
     */
    PlaceholderProcessor getPlaceholderProcessor();

    /**
     * Returns the Embed Processor
     *
     * @return Embed Processor
     */
    EmbedProcessor getEmbedProcessor();
}
