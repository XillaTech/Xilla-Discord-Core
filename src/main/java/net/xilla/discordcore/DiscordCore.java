package net.xilla.discordcore;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import net.xilla.discordcore.api.Data;
import net.xilla.discordcore.api.Log;
import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.api.config.ConfigManager;
import net.xilla.discordcore.api.staff.Staff;
import net.xilla.discordcore.api.staff.StaffManager;
import net.xilla.discordcore.api.timer.TimerManager;
import net.xilla.discordcore.commandsystem.CommandDiscord;
import net.xilla.discordcore.commandsystem.CommandManager;
import net.xilla.discordcore.commandsystem.CommandWorker;
import net.xilla.discordcore.commandsystem.cmd.End;
import net.xilla.discordcore.commandsystem.cmd.Help;
import net.xilla.discordcore.commandsystem.cmd.Settings;
import net.xilla.discordcore.module.ModuleManager;

import java.io.File;

public class DiscordCore {

    private static DiscordCore instance;

    public static DiscordCore getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new DiscordCore();
    }

    private JDA bot;

    public DiscordCore() {
        long start = System.currentTimeMillis();
        Log.sendMessage(0, "Loading Xilla Discord Core...");

        File folder = new File("modules/");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Loading Configs
        ConfigManager configManager = new ConfigManager();
        configManager.addConfig(new Config("settings.json"));
        configManager.addConfig(new Config("timers.json"));
        configManager.addConfig(new Config("reminders.json"));

        Config settings = configManager.getConfig("settings.json");
        settings.loadDefault("botToken", "example");
        settings.loadDefault("guildID", "example");
        settings.loadDefault("companyName", "example");
        settings.loadDefault("commandPrefix", ">");
        settings.loadDefault("embedColor", "#5a5a5a");
        settings.loadDefault("verbose", false);
        settings.save();

        new Log();

        Log.sendMessage(3, "Loading Command Manager...");
        new TimerManager();
        new CommandManager();
        CommandManager commandManager = CommandManager.getInstance();
        commandManager.registerCommand(new Help());
        commandManager.registerCommand(new End());
        commandManager.registerCommand(new Settings());
        commandManager.registerCommand(new net.xilla.discordcore.commandsystem.cmd.Staff());

        Log.sendMessage(3, "Loading Staff...");
        StaffManager staffManager = new StaffManager();

        Config staff = new Config("staff.json");
        ConfigManager.getInstance().addConfig(staff);

        Staff admin = new Staff("Admin", 10, "example");
        Staff mod = new Staff("Mod", 5, "example");

        staff.loadDefault(admin.getKey(), admin.toJson());
        staff.loadDefault(mod.getKey(), mod.toJson());
        staffManager.addStaff(mod);
        staffManager.addStaff(admin);

        staff.save();

        Log.sendMessage(3, "Loading Discord Bot...");
        try {
            String token = ConfigManager.getInstance().getConfig("settings.json").getString("botToken");
            this.bot = new JDABuilder(token).build();
            this.bot.addEventListener(new CommandDiscord());
            instance = this;
        } catch (Exception ex) {
            Log.sendMessage(2, "Your discord bot's token is invalid, or could not connect!");
            ex.printStackTrace();
            System.exit(0);
        }

        Log.sendMessage(3, "Loading Modules...");
        new ModuleManager();

        new CommandWorker().start();

        Log.sendMessage(0, "Finished loading " + new Data().getVersion() + " in " + ((System.currentTimeMillis() - start) / 1000.0) + "s!");

    }

    public JDA getBot() {
        return bot;
    }
}
