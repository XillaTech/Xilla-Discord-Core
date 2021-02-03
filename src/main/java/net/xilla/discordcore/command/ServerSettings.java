package net.xilla.discordcore.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.library.DiscordAPI;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.library.embed.JSONEmbed;
import net.xilla.discordcore.settings.GuildSettings;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerSettings extends GuildSettings {

    public ServerSettings() {
        super("Server-Settings", "servers/settings/");
        setDefault("embed-color", DiscordAPI.getCoreSetting().getEmbedColor());
        setDefault("disabled-modules", new ArrayList<String>());
        setDefault("disabled-commands", new ArrayList<String>());
        setDefault("core-embed", new JSONEmbed("default", new EmbedBuilder().setColor(DiscordAPI.getColor()).setFooter("%user% - %date%").setColor(DiscordAPI.getColor())).toJSON());
    }

    public boolean canRunCommand(CommandData data) {
        return canRunCommand(data, data.getCommand());
    }

    public boolean canRunCommand(CommandData data, String command) {
        return true; // disabled this function temporarily..
//        if(data.get() instanceof MessageReceivedEvent) {
//            MessageReceivedEvent event = (MessageReceivedEvent)data.get();
//
//            Guild guild;
//            try {
//                guild = event.getGuild();
//            } catch (Exception ex) {
//                return false;
//            }
//
//            List<String> commands = get(guild, "disabled-commands");
//            List<String> modules = get(guild, "disabled-modules");
//            Command cmd = DiscordCore.getInstance().getCommandManager().getCommand(command);
//
//            if (commands.contains(command.toLowerCase()) || modules.contains(cmd.getModule().toLowerCase())) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return true;
//        }
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
        return new JSONEmbed("embed", (JSONObject)get(guild, "core-embed")).getEmbedBuilder().build().getColor();
    }

    public Color getColor(String guildID) {
        return new JSONEmbed("embed", (JSONObject)get(guildID, "core-embed")).getEmbedBuilder().build().getColor();
    }

    public EmbedBuilder getEmbed(MessageReceivedEvent event) {
        JSONEmbed jsonEmbed = new JSONEmbed("default", (JSONObject)get(event.getGuild(), "core-embed"));
        EmbedBuilder embedBuilder = jsonEmbed.getEmbedBuilder();

        MessageEmbed embed = embedBuilder.build();
        if(embed.getFooter() != null && embed.getFooter().getText() != null) {
            embedBuilder.setFooter(embed.getFooter().getText().replace("%user%", event.getAuthor().getAsTag()).replace("%date%", new Date().toString()));
        }
        embedBuilder.setColor(getColor(event.getGuild()));
        return embedBuilder;
    }

    public EmbedBuilder getEmbed(String user, Guild guild) {
        JSONEmbed jsonEmbed = new JSONEmbed("default", (JSONObject)get(guild, "core-embed"));
        EmbedBuilder embedBuilder = jsonEmbed.getEmbedBuilder();

        MessageEmbed embed = embedBuilder.build();
        if(embed.getFooter() != null && embed.getFooter().getText() != null) {
            embedBuilder.setFooter(embed.getFooter().getText().replace("%user%", user).replace("%date%", new Date().toString()));
        }
        embedBuilder.setColor(getColor(guild));
        return embedBuilder;
    }

    public EmbedBuilder getEmbed(String user, String guild) {
        JSONEmbed jsonEmbed = new JSONEmbed("default", (JSONObject)get(guild, "core-embed"));
        EmbedBuilder embedBuilder = jsonEmbed.getEmbedBuilder();

        MessageEmbed embed = embedBuilder.build();
        if(embed.getFooter() != null && embed.getFooter().getText() != null) {
            embedBuilder.setFooter(embed.getFooter().getText().replace("%user%", user).replace("%date%", new Date().toString()));
        }
        embedBuilder.setColor(getColor(guild));
        return embedBuilder;
    }

}
