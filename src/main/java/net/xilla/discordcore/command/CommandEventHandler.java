package net.xilla.discordcore.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.permission.DiscordUser;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.core.command.CommandManager;
import net.xilla.discordcore.core.command.flag.CommandFlag;

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

                // Passes the command over to the internal command system (Part of the core)

                if(DiscordCore.getInstance().getPlatform().getCommandSettings().isRateLimit()) {
                    if (commandCache.containsKey(event.getAuthor().getId())) {
                        if (commandCache.get(event.getAuthor().getId()).size() > DiscordCore.getInstance().getPlatform().getCommandSettings().getRateLimit()) {
                            return;
                        }
                    }
                }
                String raw = message.substring(prefix.length());

                int level = 1;
                ArrayList<String> parts = new ArrayList<>(Arrays.asList(raw.split(" ")));
                ArrayList<String> tempparts = new ArrayList<>(parts);

                Map<CommandFlag, String> flags = new HashMap<>();
                for(int i = 1; i < parts.size(); i++) {
                    boolean found = false;
                    for(CommandFlag flag : new ArrayList<>(DiscordCore.getInstance().getCommandManager().getFlagManager().getData().values())) {

                        for(String identifier : flag.getIdentifier()) {
                            if (tempparts.get(i).equalsIgnoreCase("-" + identifier)) {
                                try {
                                    String input = parts.remove(level + 1);
                                    parts.remove(level);

                                    flags.put(flag, input);
                                    found = true;
                                    break;
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        System.out.println(parts);
                    }

                    if(!found) {
                        level++;
                    }
                }

                if(flags.size() == 0) {
                    flags = null;
                }

                String command = parts.get(0).toLowerCase();
                String[] args;
                if(parts.size() == 1) {
                    args = new String[0];
                } else {
                    args = new String[parts.size() - 1];
                    for(int i = 1; i < parts.size(); i++) {
                        args[i - 1] = parts.get(i);
                    }
                }

                if(DiscordCore.getInstance().getPlatform().getCommandSettings().isRateLimit()) {
                    if (!commandCache.containsKey(event.getAuthor().getId())) {
                        commandCache.put(event.getAuthor().getId(), new Vector<>());
                    }
                    commandCache.get(event.getAuthor().getId()).add(System.currentTimeMillis());
                }

                CommandData<MessageReceivedEvent> data = new CommandData<>(command, args, event, CoreCommandExecutor.discord_input, new DiscordUser(Objects.requireNonNull(event.getMember())), flags);
                commandManager.runCommand(data);
            }
        }
    }

}
