package net.xilla.discordcore.library.program;

import lombok.Getter;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.core.library.program.ProgramManager;
import net.xilla.core.library.program.StartupPriority;
import net.xilla.core.library.program.StartupProcess;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.library.DiscordAPI;
import net.xilla.discordcore.settings.GuildSettings;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class DiscordProgram extends ProgramManager {

    @Getter
    private static DiscordProgram program = null;

    // Listeners

    private final List<ListenerAdapter> listeners = new Vector<>();

    // Discord Settings

    @Getter
    private final Map<String, GuildSettings> discordSettings = new ConcurrentHashMap<>();

    @Getter
    private final Map<Class<? extends GuildSettings>, GuildSettings> discordSettingsRefl = new ConcurrentHashMap<>();

    private final List<GuildSettings> dSettings = new Vector<>();

    // Discord Core

    @Getter
    private final DiscordCore core;

    // Discord Controller

    @Getter
    private DiscordController discordController;

    // Constructor, duh

    public DiscordProgram(String name, boolean commandLine) {
        super(name);

        program = this;

        this.discordController = new DiscordController(this);

        this.core = new DiscordCore(Platform.getPlatform.EMBEDDED.name, "", commandLine, name);

        registerStartupProcess(new StartupProcess("Listeners", StartupPriority.LOW) {
            @Override
            public void run() {
                for(ListenerAdapter la : listeners) {
                    DiscordAPI.getBot().addEventListener(la);
                }
            }
        });

        DiscordCore.getInstance().addExecutor(() -> {
            startup();
            Logger.log(LogLevel.INFO, name + " is fully started!", getClass());
        });
    }

    public DiscordProgram(String name, DiscordCore core) {
        super(name);

        program = this;

        this.discordController = new DiscordController(this);

        this.core = core;

        registerStartupProcess(new StartupProcess("Listeners", StartupPriority.LOW) {
            @Override
            public void run() {
                for(ListenerAdapter la : listeners) {
                    DiscordAPI.getBot().addEventListener(la);
                }
            }
        });

        DiscordCore.getInstance().addExecutor(() -> {
            startup();
            Logger.log(LogLevel.INFO, name + " is fully started!", getClass());
        });
    }

    // Register stuff

    public void registerListener(ListenerAdapter la) {
        this.listeners.add(la);
    }

    public void registerListener(int i, ListenerAdapter la) {
        this.listeners.add(i, la);
    }

    public void registerSettings(GuildSettings settings) {
        this.discordSettings.put(settings.getKey().toString(), settings);
        this.discordSettingsRefl.put(settings.getClass(), settings);
        this.dSettings.add(settings);
    }

    public void registerSettings(int i, GuildSettings settings) {
        this.discordSettings.put(settings.getKey().toString(), settings);
        this.discordSettingsRefl.put(settings.getClass(), settings);
        this.dSettings.add(i, settings);
    }

}
