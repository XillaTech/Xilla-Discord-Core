package net.xilla.discordcore.settings;

import net.xilla.core.library.manager.Manager;

import java.util.Set;

public class SettingsManager extends Manager<String, DiscordSettings> {

    public SettingsManager() {
        super("Settings");
    }

    public Set<String> getSettingsNames() {
        return getData().keySet();
    }

    @Override
    public void load() {

    }

    @Override
    protected void objectAdded(DiscordSettings settings) {

    }

    @Override
    protected void objectRemoved(DiscordSettings settings) {

    }

}
