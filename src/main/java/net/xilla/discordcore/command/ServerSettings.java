package net.xilla.discordcore.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.command.Command;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.settings.GuildSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ServerSettings extends GuildSettings {

    public ServerSettings() {
        super("Server-Settings", "servers/settings/");
        setDefault("embed-color", DiscordAPI.getCoreSetting().getEmbedColor());
        setDefault("disabled-modules", new ArrayList<String>());
        setDefault("disabled-commands", new ArrayList<String>());
    }

    public boolean canRunCommand(CommandData data) {
        return canRunCommand(data, data.getCommand());
    }

    public boolean canRunCommand(CommandData data, String command) {
        if(data.get() instanceof MessageReceivedEvent) {
            MessageReceivedEvent event = (MessageReceivedEvent)data.get();

            List<String> commands = get(event.getGuild(), "disabled-commands");
            List<String> modules = get(event.getGuild(), "disabled-modules");
            Command cmd = DiscordCore.getInstance().getCommandManager().getCommand(command);

            if (commands.contains(command.toLowerCase()) || modules.contains(cmd.getModule().toLowerCase())) {
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

    public Color getColor(Guild guild) {
        return Color.decode(get(guild, "embed-color").toString());
    }

    public Color getColor(String guildID) {
        return Color.decode(get(guildID, "embed-color").toString());
    }

}
