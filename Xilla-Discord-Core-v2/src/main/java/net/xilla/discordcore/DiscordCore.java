package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.TobiasBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.xilla.discordcore.api.DiscordAPI;
import net.xilla.discordcore.api.form.form.FormHandler;
import net.xilla.discordcore.api.settings.SettingsManager;
import net.xilla.discordcore.api.startup.PostStartupExecutor;
import net.xilla.discordcore.api.startup.PostStartupManager;
import net.xilla.discordcore.command.CommandEventHandler;
import net.xilla.discordcore.api.module.ModuleManager;
import net.xilla.discordcore.core.CoreSettings;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.staff.GroupManager;

import javax.security.auth.login.LoginException;

public class DiscordCore extends CoreObject {

    private static DiscordCore instance = null;

    public static DiscordCore getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new DiscordCore(Platform.getPlatform.STANDALONE.name, null, true, "Xilla Discord Core");
    }

    private TobiasAPI api;
    private Platform platform;
    private JDA bot;
    private CoreSettings settings;
    private String type;
    private ModuleManager moduleManager;
    private SettingsManager settingsManager;
    private PostStartupManager postStartupManager;

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

            shardBuilder.setActivity(Activity.playing(settings.getActivity()));

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
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            getLog().sendMessage(0, "Running post startup executors now... Some things may only startup now!");
            postStartupManager.run();
        }).start();
    }

    public JDA getBot() {
        return bot;
    }

    public TobiasAPI getTobiasAPI() {
        return api;
    }

    public Platform getPlatform() {
        return platform;
    }

    public CoreSettings getSettings() {
        return settings;
    }

    public GroupManager getGroupManager() {
        return getPlatform().getGroupManager();
    }

    public void addExecutor(PostStartupExecutor executor) {
        postStartupManager.addExecutor(executor);
    }

    public String getType() {
        return type;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
}
