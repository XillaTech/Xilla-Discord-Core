package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.Log;
import com.tobiassteely.tobiasapi.api.config.Config;
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
                if(DiscordCore.getInstance().getPlatform().getCommandManager().runCommand(message.substring(commandPrefix.length()), event)) {
                    return;
                }
            }
        }
    }

}
