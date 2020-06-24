package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.command.CommandManager;
import com.tobiassteely.tobiasapi.command.data.CommandData;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.permission.DiscordUser;

import java.util.Objects;

public class CommandEventHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) { // Stops bots from sending commands

            String message = event.getMessage().getContentRaw(); // Gets the raw message
            String prefix = DiscordCore.getInstance().getSettings().getCommandPrefix(); // Gets the command prefix
            CommandManager commandManager = DiscordCore.getInstance().getCommandManager(); // Gets the command manager

            // First makes sure the message is longer then just the prefix to prevent errors
            // Then checks if the message starts with the prefix
            if (message.length() > prefix.length() && message.toLowerCase().startsWith(prefix.toLowerCase())) {

                // Passes the command over to the internal command system (Part of TobiasAPI)
                CommandData<MessageReceivedEvent> data = new CommandData<>(event, CoreCommandExecutor.discord_input, new DiscordUser(Objects.requireNonNull(event.getMember())));
                commandManager.runRawCommandInput(message.substring(prefix.length()), data);
            }
        }
    }

}
