package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.xilla.discordcore.command.CommandEventHandler;
import net.xilla.discordcore.command.CommandManager;
import net.xilla.discordcore.command.type.basic.BasicCommand;
import net.xilla.discordcore.command.type.basic.BasicCommandExecutor;
import net.xilla.discordcore.command.CommandResponse;
import net.xilla.discordcore.command.type.template.type.TextCommand;
import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.platform.CoreSettings;
import net.xilla.discordcore.staff.StaffManager;

import javax.security.auth.login.LoginException;

public class DiscordCore {

    private static DiscordCore instance;

    public static DiscordCore getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new DiscordCore(Platform.getPlatform.STANDALONE.getPlatform());
    }

    private TobiasAPI api;
    private Platform platform;
    private JDA bot;
    private CoreSettings settings;

    public DiscordCore(String platformType) {
        instance = this;
        this.api = new TobiasAPI(); // Loads base APIs

        this.settings = new CoreSettings(); // Loads Core Settings

        this.platform = new Platform(platformType); // Loads the rest of the core
        this.platform.getCommandManager().getCommandWorker().start();

        try {
            this.bot = new JDABuilder(DiscordCore.getInstance().getSettings().getBotToken()).build();
        } catch (LoginException ex) {
            ex.printStackTrace();
        }

        this.bot.addEventListener(new CommandEventHandler());

        getCommandManager().createSimpleCommand("YouTube", "Sends the YouTube link.", 0, "https://www.youtube.com/");

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("YouTube").setDescription("https://www.youtube.com/");
        getCommandManager().createSimpleCommand("YouTube2", "Sends the YouTube link.", 0, embedBuilder);

        getCommandManager().getTemplateManager().reload();
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

    public CommandManager getCommandManager() {
        return getPlatform().getCommandManager();
    }

    public StaffManager getStaffManager() {
        return getPlatform().getStaffManager();
    }
}
