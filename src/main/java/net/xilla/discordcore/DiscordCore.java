package net.xilla.discordcore;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.xilla.core.library.config.ConfigManager;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.manager.XillaManager;
import net.xilla.core.library.worker.Worker;
import net.xilla.core.library.worker.WorkerManager;
import net.xilla.core.log.Log;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.core.CoreSettings;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.command.CommandManager;
import net.xilla.discordcore.core.command.handler.CommandEventHandler;
import net.xilla.discordcore.library.form.form.FormHandler;
import net.xilla.discordcore.library.program.DiscordProgram;
import net.xilla.discordcore.library.program.ProgramInterface;
import net.xilla.discordcore.module.Module;
import net.xilla.discordcore.module.ModuleManager;
import net.xilla.discordcore.settings.DiscordSettings;
import net.xilla.discordcore.settings.SettingsManager;
import net.xilla.discordcore.startup.PostStartupExecutor;
import net.xilla.discordcore.startup.PostStartupManager;
import net.xilla.discordcore.startup.StartupManager;
import net.xilla.discordcore.ui.MenuManager;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DiscordCore implements ProgramInterface {

    /**
     * Used to store the main instance of the DiscordCore
     */
    private static DiscordCore instance = null;

    /**
     * Used to access the main instance of the DiscordCore
     *
     * @return DiscordCore
     */
    public static DiscordCore getInstance() {
        return instance;
    }

    /**
     * This argument is used for stand-alone application of the
     * discord core.
     *
     * @param args Java Startup Arguments
     */
    public static void main(String[] args) {
        new DiscordCore(Platform.getPlatform.STANDALONE.name, null, true, "Xilla Discord Core");
    }

    /**
     * The JDA is the Discord JDA wrapper. It runs all discord API calls
     */
    @Getter
    private JDA bot;

    /**
     * The CoreSettings stores all the main settings used for various
     * functions and features around the bot.
     */
    @Getter
    private CoreSettings settings;

    /**
     * Used to store which platform the core is being ran on
     */
    @Getter
    private String type;

    /**
     * This manager is used to manage the discord core modules
     */
    @Getter
    private ModuleManager moduleManager;

    /**
     * This manager is used to manager the settings files loaded by the core
     */
    @Getter
    private SettingsManager settingsManager;

    /**
     * This manager is used to run events after the core has connected
     * to the discord API.
     */
    @Getter
    private static PostStartupManager postStartupManager = new PostStartupManager();

    /**
     * This manager is used to run events before the core has connected
     * to the discord API.
     */
    @Getter
    private static StartupManager startupManager = new StartupManager();

    @Getter
    private CommandManager commandManager = null;

    public DiscordCore(String platform, String baseFolder, boolean startCommandLine, String name) {
        this(platform, baseFolder, startCommandLine, name, null);
    }


    public DiscordCore(String platform, String baseFolder, boolean startCommandLine, String name, String token) {
        instance = this;

        loadLogging();

        this.type = platform;

        Logger.log(LogLevel.DEBUG, "Starting discord core", getClass());
        startCore(name, baseFolder, token, startCommandLine);

        Logger.log(LogLevel.DEBUG, "Starting discord core modules", getClass());
        this.moduleManager = new ModuleManager();

        Logger.log(LogLevel.DEBUG, "Starting discord bot connection", getClass());
        startBot(token);

        Logger.log(LogLevel.DEBUG, "Finishing up the startup", getClass());
        finishedStarting(startCommandLine);
    }

    private void startCore(String name, String baseFolder, String token, boolean startCommandLine) {
        if(DiscordProgram.getProgram() == null) {
            new DiscordProgram(name, this);
        }

        if(baseFolder != null && !baseFolder.isEmpty()) {
            ConfigManager.getInstance().setBaseFolder(baseFolder);
        }

        Logger.log(LogLevel.DEBUG, "Starting discord core settings", getClass());
        this.settingsManager = new SettingsManager();
        this.settings = new CoreSettings();

        // Sets the log level
        Logger.setLogLevel(LogLevel.valueOf(settings.getLogLevel()));

        // Starting GUI
        if(settings.isConsoleGui()) {
            MenuManager.start(name);
        }

        if(token == null || token.isEmpty()) {
            // Loads settings
            if (DiscordCore.getInstance().getType().equals(Platform.getPlatform.STANDALONE.name) || DiscordCore.getInstance().getType().equals(Platform.getPlatform.EMBEDDED.name)) {
                // Fancy installer for standalone
                settings.getInstaller().install("The discord bot's token from https://discord.com/developers/", "token", "bottoken");
            }
        }

        Logger.log(LogLevel.DEBUG, "Starting discord core command manager", getClass());
        this.commandManager = new CommandManager(name, startCommandLine);
        commandManager.reload();

        Logger.log(LogLevel.DEBUG, "Starting discord core platform", getClass());
        new Platform(type);
    }

    private void startBot(String token) {
        // Connects to the discord api
        try {
            JDABuilder shardBuilder;
            if(token == null || token.isEmpty()) {
                shardBuilder = JDABuilder.createDefault(settings.getBotToken());
            } else {
                shardBuilder = JDABuilder.createDefault(token);
            }

            shardBuilder = startupManager.run(shardBuilder);;

            for (int i = 0; i < settings.getShards(); i++) {
                shardBuilder.useSharding(i, settings.getShards()).build();
            }

            if (settings.getActivity() != null && !settings.getActivity().equalsIgnoreCase("none")) {
                if (settings.getActivityType().equalsIgnoreCase("Playing")) {
                    shardBuilder.setActivity(Activity.playing(settings.getActivity()));
                } else if (settings.getActivityType().equalsIgnoreCase("Listening")) {
                    shardBuilder.setActivity(Activity.listening(settings.getActivity()));
                } else if (settings.getActivityType().equalsIgnoreCase("Streaming")) {
                    shardBuilder.setActivity(Activity.streaming(settings.getActivity(), settings.getActivityURL()));
                } else if (settings.getActivityType().equalsIgnoreCase("Watching")) {
                    shardBuilder.setActivity(Activity.watching(settings.getActivity()));
                }
            }

            this.bot = shardBuilder.build();

        } catch (LoginException ex) {
            ex.printStackTrace();
        }
    }

    private void loadLogging() {
        Logger.setLogger(new Log() {
            @Override
            public void log(LogLevel logLevel, String s, Class aClass) {
                org.slf4j.Logger logger = LoggerFactory.getLogger(aClass.getName());

                if(MenuManager.getInstance() != null) {
                    if(logLevel.ordinal() >= Logger.getLogLevel().ordinal()) {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
                        df.setTimeZone(Logger.getTimeZone());
                        String nowAsISO = df.format(new Date());
                        String log = "[" + nowAsISO + " " + Logger.getTimeZone().getID() + "] [" + logLevel.name() + "] " + s;
                        MenuManager.getInstance().getConsolePane().iterateConsole(log);
                    }
                }

                if(logLevel.equals(LogLevel.DEBUG)) {
                    logger.debug(s);
                } else if(logLevel.equals(LogLevel.INFO)) {
                    logger.info(s);
                } else if(logLevel.equals(LogLevel.WARN)) {
                    logger.warn(s);
                } else if(logLevel.equals(LogLevel.ERROR)) {
                    logger.error(s);
                } else if(logLevel.equals(LogLevel.FATAL)) {
                    logger.error(s);
                }
            }

            @Override
            public void log(LogLevel logLevel, Throwable throwable, Class aClass) {
                log(throwable, aClass);
            }

            @Override
            public void log(Throwable throwable, Class aClass) {
                org.slf4j.Logger logger = LoggerFactory.getLogger(aClass.getName());
                logger.error(aClass.getName(), throwable);

                if(MenuManager.getInstance() != null) {
                    if(LogLevel.ERROR.ordinal() >= Logger.getLogLevel().ordinal()) {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
                        df.setTimeZone(Logger.getTimeZone());
                        String nowAsISO = df.format(new Date());

                        String log = "[" + nowAsISO + " " + Logger.getTimeZone().getID() + "] [" + LogLevel.ERROR.name() + "] (" + aClass.getName() + ") " + throwable.toString();

                        MenuManager.getInstance().getConsolePane().iterateConsole(log);

                        for (int i = 0; i < throwable.getStackTrace().length; i++) {
                            log = "[" + nowAsISO + " " + Logger.getTimeZone().getID() + "] [" + LogLevel.ERROR.name() + "] (" + aClass.getName() + ") [ERR] " + throwable.getStackTrace()[i].toString();

                            MenuManager.getInstance().getConsolePane().iterateConsole(log);
                        }
                    }
                }
            }
        });
    }

    private void finishedStarting(boolean startCommandLine) {
        Logger.log(LogLevel.DEBUG, "Starting discord core command line", getClass());

        getBot().addEventListener(new CommandEventHandler());
        getBot().addEventListener(new FormHandler());

        // Starting Command Line
        if(startCommandLine) {
            getCommandManager().getCommandWorker().start();
        }

        Logger.log(LogLevel.DEBUG, "Starting  ", getClass());

        new Thread(() -> {
            try {
                bot.awaitReady();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.log(LogLevel.INFO, "Running post startup executors now... Some things may only startup now!", DiscordCore.class);
            postStartupManager.run();
        }).start();
    }

    /**
     * Used to execute events after the core has connected to the
     * discord API. Useful for things that may need to connect to the
     * API on start up.
     *
     * @param executor Startup Executor
     */
    public void addExecutor(PostStartupExecutor executor) {
        postStartupManager.addExecutor(executor);
    }

    /**
     * The function used to safely shutdown the bot...
     */
    public void shutdown() {
        Logger.log(LogLevel.DEBUG, "Shutting down all bot processes.", getClass());

        Logger.log(LogLevel.DEBUG, "Stopping workers.", getClass());
        for(Worker worker : new ArrayList<>(WorkerManager.getInstance().getData().values())) {
            Logger.log(LogLevel.DEBUG, "Stopping worker " + worker, getClass());
            worker.stopWorker();
            Logger.log(LogLevel.DEBUG, "Successfully stopped worker " + worker, getClass());
        }

        Logger.log(LogLevel.DEBUG, "Saving and disabling settings.", getClass());
        for(DiscordSettings settings : new ArrayList<>(getSettingsManager().getData().values())) {
            Logger.log(LogLevel.DEBUG, "Saving and disabling settings file " + settings, getClass());
            settings.getConfig().save();
            getSettingsManager().remove(settings);
            Logger.log(LogLevel.DEBUG, "Successfully saved and disabled settings file " + settings, getClass());
        }

        Logger.log(LogLevel.DEBUG, "Saving managers.", getClass());
        for(Manager manager : new ArrayList<>(XillaManager.getInstance().getData().values())) {
            Logger.log(LogLevel.DEBUG, "Saving manager " + manager, getClass());
            if(manager.getConfig() != null) {
                manager.save();
            }
            Logger.log(LogLevel.DEBUG, "Successfully saved manager " + manager, getClass());
        }

        Logger.log(LogLevel.DEBUG, "Disabling modules.", getClass());
        for(Module module : new ArrayList<>(getModuleManager().getData().values())) {
            Logger.log(LogLevel.DEBUG, "Disabling module " + module, getClass());
            module.onDisable();
            Logger.log(LogLevel.DEBUG, "Successfully Disabled module " + module, getClass());
        }

        this.bot.shutdown();

        instance = null;
    }

    /**
     * The function used to safely shutdown the bot...
     */
    public void restart() {
        shutdown();
        Logger.log(LogLevel.INFO, "Restarting does NOT restart the java file. If you are trying to update the core, you will need to stop and start the bot. However for modules or for small issues, a soft reboot should work.", getClass());
        DiscordCore newCore = new DiscordCore(getPlatform().getType(), ConfigManager.getInstance().getBaseFolder(), getCommandManager().isCommandLine(), getCommandManager().getName());

        for(Module module : getModuleManager().iterate()) {
            newCore.getModuleManager().put(module);
            module.onEnable();
        }
    }

    /**
     * Used to add support to pass old method calls to the new method
     */
    @Deprecated
    public Platform getPlatform() {
        return Platform.getInstance();
    }

}
