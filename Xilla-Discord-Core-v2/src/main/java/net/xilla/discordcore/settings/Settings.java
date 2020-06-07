package net.xilla.discordcore.settings;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.command.CommandManager;
import com.tobiassteely.tobiasapi.config.Config;
import com.tobiassteely.tobiasapi.config.ConfigManager;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.staff.StaffManager;

public class Settings {

    private Config config;
    private Installer installer;

    public Settings(Config config) {
        this.config = config;
        this.installer = new Installer(config);
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

    public StaffManager getStaffManager() {
        return DiscordCore.getInstance().getPlatform().getStaffManager();
    }

    public ConfigManager getConfigManager() {
        return DiscordCore.getInstance().getTobiasAPI().getConfigManager();
    }

}
