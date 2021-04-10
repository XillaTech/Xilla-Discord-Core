package net.xilla.discord.listener;

import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.api.command.CommandInput;
import net.xilla.discord.api.command.CommandProcessor;
import net.xilla.discord.api.permission.PermissionUser;
import net.xilla.discord.api.permission.UserProcessor;
import net.xilla.discord.manager.command.CommandInputWrapper;
import net.xilla.discord.setting.DiscordSettings;
import org.jetbrains.annotations.NotNull;

/**
 * This listener is strictly for command related things.
 *
 * It's used to scan the sent messages to look for discord
 * commands that can be processed by this bot.
 */
public class CommandListener extends ListenerAdapter {

    /**
     * Receives the raw input from discord and processes it.
     * Firstly it checks if it uses the command prefix system,
     * and if so, it passes it to the command processor.
     *
     * @param event Guild Message Received Event
     */
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        // Getting all the data processors I need
        DiscordCore core = DiscordCore.getInstance();
        CommandProcessor commandProcessor = core.getCommandProcessor();
        UserProcessor userProcessor = core.getUserProcessor();
        DiscordSettings discordSettings = core.getController().getSettings(DiscordSettings.class);

        // Checking if it's a command
        String prefix = discordSettings.getPrefix();
        String rawMessage = event.getMessage().getContentRaw();
        if(rawMessage.startsWith(prefix)) {

            // Preparing the command data for the processor
            String message = rawMessage.substring(prefix.length());
            String command = message.split(" ")[0];
            String input = message.substring(command.length());
            PermissionUser user = userProcessor.pull(event.getAuthor());

            // Wrapping up the data into a CommandInput for the processor
            CommandInputWrapper inputWrapper = new CommandInputWrapper();
            inputWrapper.setCommand(command);
            inputWrapper.setExecutor(user);
            inputWrapper.setRawInput(rawMessage);
            inputWrapper.setInput(input);
            inputWrapper.setEvent(event);
            inputWrapper.setArgs(input.split(" "));

            // Sending the CommandInput over to be handled.
            commandProcessor.processSafe(inputWrapper);
        }
    }

}
