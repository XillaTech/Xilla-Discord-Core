package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.TobiasBuilder;
import com.tobiassteely.tobiasapi.api.TobiasObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.xilla.discordcore.command.CommandEventHandler;
import net.xilla.discordcore.module.ModuleManager;
import net.xilla.discordcore.platform.CoreSettings;
import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.staff.StaffManager;

import javax.security.auth.login.LoginException;

public class DiscordCore extends TobiasObject {

    private static DiscordCore instance = null;

    public static DiscordCore getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new DiscordCore(Platform.getPlatform.STANDALONE.name, null);
    }

    private TobiasAPI api;
    private Platform platform;
    private JDA bot;
    private CoreSettings settings;
    private String type;
    private ModuleManager moduleManager;

    public DiscordCore(String platform, String baseFolder) {
        instance = this;
        this.type = platform;

        // Loads base APIs
        boolean commandLine = platform.equals(Platform.getPlatform.STANDALONE.name);
        TobiasBuilder builder = new TobiasBuilder().loadCommandManager("Xilla Discord Core", commandLine);
        this.api = builder.loadConfigManager(baseFolder).build();

        // Loads Core Settings
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

            shardBuilder.setActivity(Activity.playing(settings.getActivity()));

            this.bot = shardBuilder.build();

        } catch (LoginException ex) {
            ex.printStackTrace();
        }

        // Loads up template commands
        getPlatform().getTemplateManager().reload();

        // Loads up the modules
        this.moduleManager = new ModuleManager(baseFolder);
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

    public StaffManager getStaffManager() {
        return getPlatform().getStaffManager();
    }

    public String getType() {
        return type;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

}
