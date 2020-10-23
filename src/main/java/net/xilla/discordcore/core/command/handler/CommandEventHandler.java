package net.xilla.discordcore.core.command.handler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.CoreCommandExecutor;
import net.xilla.discordcore.core.permission.PermissionAPI;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.core.command.CommandManager;
import net.xilla.discordcore.core.command.flag.CommandFlag;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

                // Passes the command over to the internal command system (Part of the core)

                String raw = message.substring(prefix.length());

                ArrayList<String> parts = new ArrayList<>(Arrays.asList(raw.split(" ")));
                ArrayList<String> tempparts = new ArrayList<>(parts);

                Map<CommandFlag, String> flags = new HashMap<>();
                for(int i = 1; i < tempparts.size() - 1; i++) {
                    for(CommandFlag flag : new ArrayList<>(DiscordCore.getInstance().getCommandManager().getFlagManager().getData().values())) {

                        for(String identifier : flag.getIdentifier()) {
                            if (tempparts.get(i).equalsIgnoreCase("-" + identifier)) {
                                try {
                                    parts.remove(tempparts.get(i + 1));
                                    parts.remove(tempparts.get(i));

                                    flags.put(flag, tempparts.get(i + 1));
                                    break;
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
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

                CommandData<MessageReceivedEvent> data = new CommandData<>(command, args, event, CoreCommandExecutor.discord_input, PermissionAPI.getUser(event.getMember()), flags);
                commandManager.runCommand(data);
            }
        }
    }

}
