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
import net.xilla.discordcore.core.manager.GuildManager;
import net.xilla.discordcore.library.DiscordAPI;
import net.xilla.discordcore.settings.GuildSettings;
import net.xilla.discordcore.startup.StartupExecutor;

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

    // Discord Managers

    @Getter
    private final Map<String, GuildManager> discordManagers = new ConcurrentHashMap<>();

    @Getter
    private final Map<Class<? extends GuildManager>, GuildManager> discordManagersRefl = new ConcurrentHashMap<>();

    private final List<GuildManager> dManagers = new Vector<>();

    // Discord Core

    @Getter
    private final DiscordCore core;

    // Discord Controller

    @Getter
    private DiscordController discordController;

    // Constructor, duh

    public DiscordProgram(String name, boolean commandLine, StartupExecutor... executors) {
        super(name);

        program = this;

        this.discordController = new DiscordController(this);

        for(StartupExecutor executor : executors) {
            DiscordCore.getStartupManager().addExecutor(executor);
        }

        this.core = new DiscordCore(Platform.getPlatform.EMBEDDED.name, "", commandLine, name);

        registerStartupProcess(new StartupProcess("Listeners", StartupPriority.LOW) {
            @Override
            public void run() {
                for(ListenerAdapter la : listeners) {
                    DiscordAPI.getBot().addEventListener(la);
                }
            }
        });

        registerStartupProcess(new StartupProcess("GuildManagers", StartupPriority.CORE_MANAGERS) {
            @Override
            public void run() {
                for(GuildManager gm : dManagers) {
                    gm.load();
                }
            }
        });

        DiscordCore.getInstance().addExecutor(() -> {
            Logger.log(LogLevel.DEBUG, "Starting discord bot " + name, getClass());
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
            Logger.log(LogLevel.DEBUG, "Starting discord bot " + name, getClass());
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

    public void registerManager(GuildManager manager) {
        this.discordManagers.put(manager.getName(), manager);
        this.discordManagersRefl.put(manager.getClass(), manager);
        this.dManagers.add(manager);
    }

    public void registerManager(int i, GuildManager manager) {
        this.discordManagers.put(manager.getName(), manager);
        this.discordManagersRefl.put(manager.getClass(), manager);
        this.dManagers.add(i, manager);
    }

}
