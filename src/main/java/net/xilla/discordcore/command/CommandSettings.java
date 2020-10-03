package net.xilla.discordcore.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.settings.Settings;
import org.json.simple.JSONArray;

public class CommandSettings extends Settings {

    public CommandSettings() {
        super("Commands", "command-settings.json");
        getConfig().setDefault("rate-limit-toggle", true);
        getConfig().setDefault("rate-limit", 3);
        getConfig().setDefault("rate-limit-seconds", 5);
        getConfig().save();
    }

    public boolean isRateLimit() {
        return getConfig().getBoolean("rate-limit-toggle");
    }

    public int getRateLimit() {
        return getConfig().getInt("rate-limit");
    }

    public int getRateLimitSeconds() {
        return getConfig().getInt("rate-limit-seconds");
    }

    public boolean isCommand(CommandData data) {
        return isCommand(data, data.getCommand());
    }

    public boolean isCommand(CommandData data, String command) {
        if(data.get() instanceof MessageReceivedEvent) {
            MessageReceivedEvent event = (MessageReceivedEvent)data.get();
            JSONArray jsonArray = getConfig().getList(event.getGuild().getId());
            if (jsonArray != null) {
                return !jsonArray.contains(command.toLowerCase());
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void setCommand(String serverID, String command, boolean type) {
        JSONArray jsonArray = getConfig().getList(serverID);
        if (jsonArray == null) {
            jsonArray = new JSONArray();
        }
        if(!type) {
            if(!jsonArray.contains(command.toLowerCase())) {
                jsonArray.add(command.toLowerCase());
            }
        } else {
            if(jsonArray.contains(command.toLowerCase())) {
                jsonArray.remove(command.toLowerCase());
            }
        }
        getConfig().set(serverID, jsonArray);
        getConfig().save();
    }

    public void removeServer(String serverID) {
        getConfig().set(serverID, null);
        getConfig().save();
    }

}
