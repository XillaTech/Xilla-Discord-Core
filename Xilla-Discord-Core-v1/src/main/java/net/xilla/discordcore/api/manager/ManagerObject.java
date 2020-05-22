package net.xilla.discordcore.api.manager;

import net.xilla.discordcore.api.config.Config;
import org.json.simple.JSONObject;

public class ManagerObject {

    private String key;
    private Config config;

    public ManagerObject(String key, Config config) {
        this.key = key;
        this.config = config;
    }

    public String getKey() {
        return key;
    }

    public Config getConfig() {
        return config;
    }

    public JSONObject toJson() {
        return null; // OVERRIDE
    }
}
