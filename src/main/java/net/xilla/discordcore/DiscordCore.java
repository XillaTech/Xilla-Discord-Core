package net.xilla.discordcore;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.TobiasBuilder;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.api.worker.Worker;
import com.tobiassteely.tobiasapi.command.CommandManager;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.xilla.discordcore.command.CommandCheck;
import net.xilla.discordcore.command.CommandEventHandler;
import net.xilla.discordcore.command.CommandSettings;
import net.xilla.discordcore.core.CoreSettings;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.staff.GroupManager;
import net.xilla.discordcore.form.form.FormHandler;
import net.xilla.discordcore.form.form.FormManager;
import net.xilla.discordcore.module.Module;
import net.xilla.discordcore.module.ModuleManager;
import net.xilla.discordcore.settings.Settings;
import net.xilla.discordcore.settings.SettingsManager;
import net.xilla.discordcore.startup.PostStartupExecutor;
import net.xilla.discordcore.startup.PostStartupManager;
import org.slf4j.LoggerFactory;

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
     * This manager is used to disable commands and modules per discord server
     */
    @Getter
    private CommandSettings commandSettings;

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

    /**
     * This is used to store the main library (ignore that it's called
     * an API, it is a library).
     */
    @Getter
    private TobiasAPI api;

    public DiscordCore(String platform, String baseFolder, boolean startCommandLine, String name) {
        instance = this;
        this.type = platform;

        this.postStartupManager = new PostStartupManager();

        // Loads base APIs
        boolean commandLine = platform.equals(Platform.getPlatform.STANDALONE.name) || platform.equals(Platform.getPlatform.EMBEDDED.name);
        TobiasBuilder builder = new TobiasBuilder().loadCommandManager(name, commandLine);
        this.api = builder.loadConfigManager(baseFolder).build(false);

        // Loads Core Settings
        this.settingsManager = new SettingsManager();
        this.settings = new CoreSettings();
        this.commandSettings = new CommandSettings();
        this.getCommandManager().setCommandRunCheck(new CommandCheck());

        setLogLevel(settings.getLogLevel(), "net.dv8tion.jda");

        this.formManager = new FormManager();

        // Loads the rest of the core
        this.platform = new Platform(platform);

        // Connects to the discord api
        try {
            JDABuilder shardBuilder = new JDABuilder(settings.getBotToken());

            for (int i = 0; i < settings.getShards(); i++) {
                shardBuilder.useSharding(i, settings.getShards()).build();
            }

            shardBuilder.addEventListeners(new CommandEventHandler());
            shardBuilder.addEventListeners(new FormHandler());

            if(settings.getActivity() != null && !settings.getActivity().equalsIgnoreCase("none")) {
                shardBuilder.setActivity(Activity.playing(settings.getActivity()));
            }

            this.bot = shardBuilder.build();

        } catch (LoginException ex) {
            ex.printStackTrace();
        }


        // Loads up the modules
        this.moduleManager = new ModuleManager(baseFolder);

        // Starting Command Line
        if(startCommandLine) {
            api.getCommandManager().getCommandWorker().start();
        }

        // Loads up template commands
        getPlatform().getTemplateManager().reload();

        // Starts up the API
        new DiscordAPI();

        new Thread(() -> {
            try {
                bot.awaitReady();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getLog().sendMessage(0, "Running post startup executors now... Some things may only startup now!");
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


    private void setLogLevel(String logLevel, String packageName) {
        ((ch.qos.logback.classic.Logger)LoggerFactory.getLogger(packageName)) .setLevel(Level.toLevel(logLevel));
    }

    /**
     * The function used to safely shutdown the bot...
     */
    public void shutdown() {
        for(Worker worker : new ArrayList<>(getTobiasAPI().getWorkerManager().getList())) {
            worker.stopWorker();
        }

        for(Module module : new ArrayList<>(getModuleManager().getList())) {
            module.onDisable();
        }

        for(Settings settings : new ArrayList<>(getSettingsManager().getList())) {
            settings.getConfig().save();
            getSettingsManager().removeObject(settings.getKey());
        }

        for(ManagerParent manager : new ArrayList<>(getTobiasAPI().getManager().getManagers())) {
            manager.save();
            getTobiasAPI().getManager().getManagers().remove(manager);
        }

        this.bot.shutdown();

        this.api = null;
        this.bot = null;
        this.formManager = null;
        this.postStartupManager = null;
        this.commandSettings = null;
        this.settings = null;
        this.platform = null;
        this.type = null;
        this.type = null;

        instance = null;
    }

    /**
     * The function used to safely shutdown the bot...
     */
    public void restart() {
        shutdown();
        getLog().sendMessage(1, "Restarting does NOT restart the java file. If you are trying to update the core, you will need to stop and start the bot. However for modules or for small issues, a soft reboot should work.");
        new DiscordCore(Platform.getPlatform.STANDALONE.name, null, true, "Xilla Discord Core");
    }

    /**
     * Returns the Command Manager for easy access
     *
     * @return Command Manager
     */
    public CommandManager getCommandManager() {
        return api.getCommandManager();
    }

}
