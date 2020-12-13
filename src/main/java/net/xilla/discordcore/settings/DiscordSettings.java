package net.xilla.discordcore.settings;

import net.xilla.core.library.config.Config;
import net.xilla.core.library.config.ConfigManager;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.XillaManager;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.Platform;

public class DiscordSettings extends ManagerObject {

    private Config config;
    private Installer installer;

    public DiscordSettings(String name, String configName) {
        super(name.toLowerCase(), XillaManager.getInstance().get("Settings"));
        this.config = new Config(configName);
        ConfigManager.getInstance().put(config);
        this.installer = new Installer(config);
        DiscordCore.getInstance().getSettingsManager().put(this);
    }

    public Config getConfig() {
        return config;
    }

    public Installer getInstaller() {
        return installer;
    }

    public Platform getPlatform() {
        return DiscordCore.getInstance().getPlatform();
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();
        json.put("name", getKey());
        json.put("config", getConfig().getKey());
        json.put("data", config.getJson().getJson());
        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }
}
