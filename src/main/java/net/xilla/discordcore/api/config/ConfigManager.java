package net.xilla.discordcore.api.config;

import net.xilla.discordcore.api.manager.ManagerParent;

public class ConfigManager extends ManagerParent {

    private static ConfigManager instance;

    public static ConfigManager getInstance() {
        return instance;
    }

    public ConfigManager() {
        instance = this;
    }

    public Config getConfig(String configName) {
        if(getObjectWithKey(configName) == null)
            addConfig(new Config(configName));
        return (Config)getObjectWithKey(configName);
    }

    public void addConfig(Config config) {
        addObject(config);
    }

}
