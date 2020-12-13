package net.xilla.discordcore.settings;

import net.xilla.core.library.manager.Manager;

public class GuildSettingsManager extends Manager<String, GuildSettings> {

    public GuildSettingsManager() {
        super("GuildSettings");
    }

    @Override
    protected void load() {

    }

    @Override
    protected void objectAdded(GuildSettings guildSettings) {

    }

    @Override
    protected void objectRemoved(GuildSettings guildSettings) {

    }
}
