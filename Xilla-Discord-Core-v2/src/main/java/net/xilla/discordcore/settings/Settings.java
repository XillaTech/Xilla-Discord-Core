package net.xilla.discordcore.settings;

import com.tobiassteely.tobiasapi.api.config.Config;

import java.util.Scanner;

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

}
