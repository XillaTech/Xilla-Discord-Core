package net.xilla.discordcore.library.program;

import net.xilla.core.library.program.ProgramController;
import net.xilla.discordcore.core.manager.GuildManager;
import net.xilla.discordcore.settings.GuildSettings;

public class DiscordController extends ProgramController {

    private DiscordProgram discordProgram;

    public DiscordController(DiscordProgram manager) {
        super(manager);

        this.discordProgram = manager;
    }

    public <T extends GuildSettings> T getGuildSettings(String name) {
        return (T)discordProgram.getDiscordSettings().get(name);
    }

    public <T extends GuildSettings> T getGuildSettings(Class clazz) {
        return (T)discordProgram.getDiscordSettingsRefl().get(clazz);
    }

    public <T extends GuildManager> T getGuildManager(String name) {
        return (T)discordProgram.getDiscordManagers().get(name);
    }

    public <T extends GuildManager> T getGuildManager(Class clazz) {
        return (T)discordProgram.getDiscordManagersRefl().get(clazz);
    }

}