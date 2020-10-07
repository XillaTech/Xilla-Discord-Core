package net.xilla.discordcore.core.server;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.config.Config;
import net.xilla.core.library.config.ConfigManager;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.XillaManager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.settings.Installer;
import net.xilla.discordcore.settings.Settings;
import org.json.simple.JSONArray;

import java.util.HashMap;

public class ServerSettings extends Config {

    @Getter
    private HashMap<String, Object> defaults;

    public ServerSettings(String serverID) {
        super("settings/" + serverID + ".json");
        this.defaults = new HashMap<>();

        defaults.put("blocked-commands", new JSONArray());
    }

    @Override
    public <T> T get(String key) {
        if(!getJson().containsKey(key)) {
            if(defaults.containsKey(key)) {
                set(key, defaults.get(key));
            } else {
                Logger.log(LogLevel.ERROR, "Failed to pull variable " + key + " from the server " + getKey() + " due to it's default variable missing!", getClass());
            }
        }
        return super.get(key);
    }

}
