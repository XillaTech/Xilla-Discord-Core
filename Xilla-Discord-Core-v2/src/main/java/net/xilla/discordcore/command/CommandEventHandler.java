package net.xilla.discordcore.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.DiscordCore;

public class CommandEventHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(!event.getAuthor().isBot()) {
            String commandPrefix = DiscordCore.getInstance().getSettings().getCommandPrefix();

            String message = event.getMessage().getContentRaw();
            if (message.length() > commandPrefix.length() && message.substring(0, commandPrefix.length()).equalsIgnoreCase(commandPrefix)) {

                DiscordCore.getInstance().getCommandManager().runRawCommandInput(message.substring(commandPrefix.length()), CoreCommandExecutor.discord_input, event);

            }
        }
    }

}
