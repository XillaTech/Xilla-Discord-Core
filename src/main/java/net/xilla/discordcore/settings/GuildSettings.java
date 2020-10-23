package net.xilla.discordcore.settings;

import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class GuildSettings {

    private String name;
    private String folder;
    private Map<String, Settings> settingsMap;
    private Map<String, Object> defaults;

    public GuildSettings(String name, String folder) {
        this.name = name;
        this.folder = folder;
        this.settingsMap = new HashMap<>();
        this.defaults = new HashMap<>();
    }

    public void setDefault(String key, Object obj) {
        this.defaults.put(key, obj);
    }

    public <T> T get(String guildID, String key) {
        return getSettings(guildID).getConfig().get(key);
    }

    public <T> T get(Guild guild, String key) {
        return getSettings(guild.getId()).getConfig().get(key);
    }

    public void set(String guildID, String key, Object value) {
        getSettings(guildID).getConfig().set(key, value);
    }

    public void set(Guild guild, String key, Object value) {
        getSettings(guild.getId()).getConfig().set(key, value);
    }

    public Settings getSettings(String guildID) {
        if(!settingsMap.containsKey(guildID)) {
            Settings settings = new Settings(guildID + "-" + name, folder + guildID + ".json");
            for(String key : defaults.keySet()) {
                settings.getConfig().setDefault(key, defaults.get(key));
            }
            settings.getConfig().save();
            settingsMap.put(guildID, settings);
        }
        return settingsMap.get(guildID);
    }

}
