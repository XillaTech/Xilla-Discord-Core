package net.xilla.rssposter.server;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.core.library.config.Config;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.settings.Settings;
import org.json.simple.JSONArray;

import java.awt.*;
import java.util.HashMap;

public class ServerSettings extends Config {

    private static HashMap<String, ServerSettings> cache = new HashMap<>();

    public static ServerSettings getSettings(Guild guild) {
        return getSettings(guild.getId());
    }

    public static ServerSettings getSettings(String guildID) {
        if(cache.containsKey(guildID)) {
            return cache.get(guildID);
        }
        return new ServerSettings(guildID);
    }

    private String guildID;

    public ServerSettings(String guildID) {
        super("servers/" + guildID + ".json");

        this.guildID = guildID;

        setDefault("color", "#018ed1");
        setDefault("channel", "none");
        setDefault("admins", new JSONArray());

        cache.put(guildID, this);
    }

    public Color getColor() {
        return Color.decode(get("color"));
    }

    public TextChannel getChannel() {
        if(getString("channel").equalsIgnoreCase("none")) {
            Guild guild = DiscordCore.getInstance().getBot().getGuildById(guildID);
            if(guild == null) {
                return null;
            }

            return guild.getTextChannels().get(0);
        } else {
            return DiscordCore.getInstance().getBot().getTextChannelById(getString("channel"));
        }
    }

    public JSONArray getAdmins() {
        return get("admins");
    }


    public void setAdmins(JSONArray array) {
        set("admins", array);
    }

}
