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
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.command.ServerSettings;
import net.xilla.discordcore.core.CommandCheck;
import net.xilla.discordcore.core.CoreSettings;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.command.CommandManager;
import net.xilla.discordcore.core.command.handler.CommandEventHandler;
import net.xilla.discordcore.core.permission.group.GroupManager;
import net.xilla.discordcore.embed.EmbedManager;
import net.xilla.discordcore.form.form.FormHandler;
import net.xilla.discordcore.form.form.FormManager;
import net.xilla.discordcore.module.Module;
import net.xilla.discordcore.module.ModuleManager;
import net.xilla.discordcore.settings.GuildSettingsManager;
import net.xilla.discordcore.settings.Settings;
import net.xilla.discordcore.settings.SettingsManager;
import net.xilla.discordcore.startup.PostStartupExecutor;
import net.xilla.discordcore.startup.PostStartupManager;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public class DiscordCore extends CoreObject {

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
     * The Platform class is used to store information and managers
     * that depend on the specific platform. As the core supports
     * various platforms.
     */
    @Getter
    private Platform platform;

    /**
     * The JDA is the Discord JDA wrapper. It runs all discord API calls
     */
    @Getter
    private JDA bot;

    /**
     * The manager used to manage, edit, and control embeds.
     */
    @Getter
    private EmbedManager embedManager;

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
     * This manager is used to manager the guild settings files loaded by the core
     */
    @Getter
    private GuildSettingsManager guildSettingsManager;

    /**
     * This manager is used to disable commands and modules per discord server
     */
    @Getter
    private ServerSettings serverSettings;

    /**
     * This manager is used to run events after the core has connected
     * to the discord API.
     */
    private PostStartupManager postStartupManager;

    /**
     * This manager is used to process the built in forms to collect
     * command data and such from discord users.
     */
    @Getter
    private FormManager formManager;

    @Getter
    private CommandManager commandManager;

    public DiscordCore(String platform, String baseFolder, boolean startCommandLine, String name) {
        instance = this;
        this.type = platform;

        if(baseFolder != null) {
            ConfigManager.getInstance().setBaseFolder(baseFolder);
        }

        this.postStartupManager = new PostStartupManager();

        // Loads base APIs
        this.commandManager = new CommandManager(name, startCommandLine);
        commandManager.reload();

        // Loads Core Settings
        this.settingsManager = new SettingsManager();
        this.guildSettingsManager = new GuildSettingsManager();
        this.settings = new CoreSettings();
        this.serverSettings = new ServerSettings();
        this.getCommandManager().setCommandRunCheck(new CommandCheck());

        this.formManager = new FormManager();

        // Loads the rest of the core
        this.platform = new Platform(platform);

        // Connects to the discord api
        try {
            JDABuilder shardBuilder = JDABuilder.createDefault(settings.getBotToken());

            for (int i = 0; i < settings.getShards(); i++) {
                shardBuilder.useSharding(i, settings.getShards()).build();
            }

            shardBuilder.addEventListeners(new CommandEventHandler());
            shardBuilder.addEventListeners(new FormHandler());

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


        // Loads up the modules
        this.moduleManager = new ModuleManager(baseFolder);

        this.embedManager = new EmbedManager();

        // Starting Command Line
        if(startCommandLine) {
            getCommandManager().getCommandWorker().start();
        }

        // Starts up the API
        new DiscordAPI();

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
     * Pulls and returns the group manager from the Platform system
     *
     * @return GroupManager
     */
    public GroupManager getGroupManager() {
        return getPlatform().getGroupManager();
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

        for(Module module : new ArrayList<>(getModuleManager().getData().values())) {
            module.onDisable();
        }

        for(Worker worker : new ArrayList<>(WorkerManager.getInstance().getData().values())) {
            worker.stopWorker();
        }

        for(Settings settings : new ArrayList<>(getSettingsManager().getData().values())) {
            settings.getConfig().save();
            getSettingsManager().remove(settings.getKey());
        }

        for(Manager manager : new ArrayList<>(XillaManager.getInstance().getData().values())) {
            if(manager.getConfig() != null) {
                manager.save();
            }
        }

        this.bot.shutdown();

        this.bot = null;
        this.formManager = null;
        this.postStartupManager = null;
        this.serverSettings = null;
        this.settings = null;
        this.platform = null;
        this.type = null;

        instance = null;
    }

    /**
     * The function used to safely shutdown the bot...
     */
    public void restart() {
        shutdown();
        Logger.log(LogLevel.INFO, "Restarting does NOT restart the java file. If you are trying to update the core, you will need to stop and start the bot. However for modules or for small issues, a soft reboot should work.", getClass());
        new DiscordCore(Platform.getPlatform.STANDALONE.name, null, true, "Xilla Discord Core");
    }

}
