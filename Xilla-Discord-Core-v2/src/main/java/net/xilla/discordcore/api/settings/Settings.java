package net.xilla.discordcore.api.settings;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.command.CommandManager;
import com.tobiassteely.tobiasapi.config.Config;
import com.tobiassteely.tobiasapi.config.ConfigManager;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.staff.GroupManager;

public class Settings extends ManagerObject {

    private Config config;
    private Installer installer;

    public Settings(String name, String configName) {
        super(name.toLowerCase());
        this.config = DiscordCore.getInstance().getConfigManager().getConfig(configName);
        this.installer = new Installer(config);
        DiscordCore.getInstance().getSettingsManager().addSettings(this);
    }

    public Config getConfig() {
        return config;
    }

    public Installer getInstaller() {
        return installer;
    }

    public DiscordCore getDiscordCore() {
        return DiscordCore.getInstance();
    }

    public Platform getPlatform() {
        return DiscordCore.getInstance().getPlatform();
    }

    public TobiasAPI getTobiasAPI() {
        return DiscordCore.getInstance().getTobiasAPI();
    }

    public CommandManager getCommandManager() {
        return DiscordCore.getInstance().getPlatform().getCommandManager();
    }

    public GroupManager getGroupManager() {
        return DiscordCore.getInstance().getPlatform().getGroupManager();
    }

    public ConfigManager getConfigManager() {
        return DiscordCore.getInstance().getTobiasAPI().getConfigManager();
    }

}
