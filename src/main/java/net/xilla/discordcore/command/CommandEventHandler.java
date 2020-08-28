package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.command.CommandData;
import com.tobiassteely.tobiasapi.command.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.permission.DiscordUser;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CommandEventHandler extends ListenerAdapter {

    private static ConcurrentHashMap<String, List<Long>> commandCache = new ConcurrentHashMap<>();;

    public static void processCache() {
        for (String key : commandCache.keySet()) {
            for (long value : new Vector<>(commandCache.get(key))) {
                if (System.currentTimeMillis() - value > DiscordCore.getInstance().getPlatform().getCommandSettings().getRateLimitSeconds() * 1000) {
                    commandCache.get(key).remove(value);
                }
            }
            if (commandCache.get(key).size() == 0) {
                commandCache.remove(key);
            }
        }
    }

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

                if(DiscordCore.getInstance().getPlatform().getCommandSettings().isRateLimit()) {
                    if (commandCache.containsKey(event.getAuthor().getId())) {
                        if (commandCache.get(event.getAuthor().getId()).size() > DiscordCore.getInstance().getPlatform().getCommandSettings().getRateLimit()) {
                            return;
                        }
                    }
                }
                String raw = message.substring(prefix.length());
                String command = raw.split(" ")[0].toLowerCase();
                String[] args;
                if(raw.split(" ").length == 1) {
                    args = new String[] {};
                } else {
                    args = raw.substring(command.length() + 1).split(" ");
                }

                if(DiscordCore.getInstance().getPlatform().getCommandSettings().isRateLimit()) {
                    if (!commandCache.containsKey(event.getAuthor().getId())) {
                        commandCache.put(event.getAuthor().getId(), new Vector<>());
                    }
                    commandCache.get(event.getAuthor().getId()).add(System.currentTimeMillis());
                }

                CommandData<MessageReceivedEvent> data = new CommandData<>(command, args, event, CoreCommandExecutor.discord_input, new DiscordUser(Objects.requireNonNull(event.getMember())));
                commandManager.runCommand(data);
            }
        }
    }

}
