package net.xilla.discordcore.settings;

import net.xilla.core.library.config.Config;
import net.xilla.core.library.config.Settings;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.Platform;

public class DiscordSettings extends Settings {

    private Installer installer;

    public DiscordSettings(String configName) {
        super(configName);
        this.installer = new Installer(super.getManager().getConfig());
    }

    public Config getConfig() {
        return getManager().getConfig();
    }

    public Installer getInstaller() {
        return installer;
    }

    public Platform getPlatform() {
        return DiscordCore.getInstance().getPlatform();
    }

}
