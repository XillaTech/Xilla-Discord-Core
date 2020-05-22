package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.settings.type.CoreSettings;

import javax.security.auth.login.LoginException;

public class DiscordCore {

    private static DiscordCore instance;

    public static DiscordCore getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new DiscordCore(new Platform(Platform.getPlatform.STANDALONE.getPlatform()));
    }

    private TobiasAPI api;
    private Platform platform;
    private JDA bot;
    private CoreSettings settings;

    public DiscordCore(Platform platform) {
        instance = this;
        this.api = new TobiasAPI(); // Loads base APIs

        this.settings = new CoreSettings(); // Loads Core Settings

        this.platform = platform; // Loads the rest of the core
        this.platform.getCommandManager().getCommandWorker().start();

        try {
            this.bot = new JDABuilder(DiscordCore.getInstance().getSettings().getBotToken()).build();
        } catch (LoginException ex) {
            ex.printStackTrace();
        }

    }

    public JDA getBot() {
        return bot;
    }

    public TobiasAPI getApi() {
        return api;
    }

    public Platform getPlatform() {
        return platform;
    }

    public CoreSettings getSettings() {
        return settings;
    }
}
