package net.xilla.discordcore.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.command.Command;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.settings.GuildSettings;
import net.xilla.discordcore.settings.Settings;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ServerSettings extends GuildSettings {

    public ServerSettings() {
        super("Server-Settings", "servers/settings/");
        setDefault("rate-limit-toggle", true);
        setDefault("rate-limit", 3);
        setDefault("rate-limit-seconds", 5);
        setDefault("disabled-modules", new ArrayList<String>());
        setDefault("disabled-commands", new ArrayList<String>());
    }

    public boolean isRateLimit(Guild guild) {
        return get(guild, "rate-limit-toggle");
    }

    public int getRateLimit(Guild guild) {
        return Integer.parseInt(get(guild, "rate-limit").toString());
    }

    public int getRateLimitSeconds(Guild guild) {
        return Integer.parseInt(get(guild, "rate-limit-seconds").toString());
    }

    public boolean isCommand(CommandData data) {
        return isCommand(data, data.getCommand());
    }

    public boolean isCommand(CommandData data, String command) {
        if(data.get() instanceof MessageReceivedEvent) {
            MessageReceivedEvent event = (MessageReceivedEvent)data.get();

            List<String> commands = get(event.getGuild(), "disabled-commands");
            List<String> modules = get(event.getGuild(), "disabled-modules");
            Command cmd = DiscordCore.getInstance().getCommandManager().getCommand(data.getCommand());

            if (commands.contains(command.toLowerCase()) && modules.contains(cmd.getModule().toLowerCase()) ) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void addModule(Guild guild, String module) {
        List<String> modules = get(guild, "disabled-modules");
        modules.add(module.toLowerCase());
        set(guild, "disabled-modules", modules);
        getSettings(guild.getId()).getConfig().save();
    }

    public void addCommand(Guild guild, String command) {
        List<String> commands = get(guild, "disabled-commands");
        commands.add(command.toLowerCase());
        set(guild, "disabled-commands", commands);
        getSettings(guild.getId()).getConfig().save();
    }

    public void removeModule(Guild guild, String module) {
        List<String> modules = get(guild, "disabled-modules");
        modules.remove(module.toLowerCase());
        set(guild, "disabled-modules", modules);
        getSettings(guild.getId()).getConfig().save();
    }

    public void removeCommand(Guild guild, String command) {
        List<String> commands = get(guild, "disabled-commands");
        commands.remove(command.toLowerCase());
        set(guild, "disabled-commands", commands);
        getSettings(guild.getId()).getConfig().save();
    }

    public void removeServer(String serverID) {
        getSettings(serverID).getConfig().clear();
        getSettings(serverID).getConfig().save();
    }

}
