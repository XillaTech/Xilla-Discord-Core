package net.xilla.discordcore.commandsystem;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.api.config.ConfigManager;

public class CommandDiscord extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(!event.getAuthor().isBot()) {
            Config config = DiscordCore.getInstance().getConfigManager().getConfig("settings.json");
            String commandPrefix = config.getString("commandPrefix");

            String message = event.getMessage().getContentRaw();
            if (message.length() > commandPrefix.length() && message.substring(0, commandPrefix.length()).equalsIgnoreCase(commandPrefix)) {
                if(DiscordCore.getInstance().getCommandManager().runCommand(message.substring(commandPrefix.length()), event)) {
                    return;
                }
            }
            DiscordCore.getInstance().getMessageEventManger().runEvent(event);
        }
    }
}
