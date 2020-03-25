package net.xilla.discordcore;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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
import net.xilla.discordcore.commandsystem.MessageEventManger;
import net.xilla.discordcore.commandsystem.cmd.End;
import net.xilla.discordcore.commandsystem.cmd.Help;
import net.xilla.discordcore.commandsystem.cmd.Settings;
import net.xilla.discordcore.installer.InstallerManager;
import net.xilla.discordcore.installer.InstallerObject;
import net.xilla.discordcore.installer.SettingObject;
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

    private ModuleManager moduleManager;
    private InstallerManager installerManager;
    private CommandManager commandManager;
    private StaffManager staffManager;
    private ConfigManager configManager;
    private CommandWorker commandWorker;
    private TimerManager timerManager;
    private MessageEventManger messageEventManger;

    private JDA bot;

    public DiscordCore() {
        instance = this;

        long start = System.currentTimeMillis();
        Log.sendMessage(0, "Loading Xilla Discord Core...");

//        Log.sendMessage(3, "Loading Config Manager...");

        // Loading Configs
        this.configManager = new ConfigManager(); // Initializes the config manager

//        Log.sendMessage(3, "Loading Configs...");

        configManager.addConfig(new Config("settings.json")); // Loads settings.json (general settings)
        configManager.addConfig(new Config("timers.json")); // Loads timers.json (timers)
        configManager.addConfig(new Config("staff.json")); // Loads staff.json (staff settings)

//        Log.sendMessage(3, "Loading Settings Config Defaults...");

        // Sets defaults for settings.json
        Config settings = configManager.getConfig("settings.json");
        settings.loadDefault("botToken", "example");
        settings.loadDefault("guildID", "example");
        settings.loadDefault("companyName", "example");
        settings.loadDefault("commandPrefix", "-");
        settings.loadDefault("embedColor", "#5a5a5a");
        settings.loadDefault("verbose", false);
        settings.save();

//        Log.sendMessage(3, "Loading Log Manager...");

        new Log(); // Loads the logging system's config, leaving this as a static instance just for ease of use...

        Log.sendMessage(3, "Loading Timer Manager...");

        this.timerManager = new TimerManager(); // Initializes the Timer Manager

        Log.sendMessage(3, "Loading Command Manager...");
        this.commandManager = new CommandManager();

        Log.sendMessage(3, "Loading Core Commands...");
        commandManager.registerCommand(new Help());
        commandManager.registerCommand(new End());
        commandManager.registerCommand(new Settings());
        commandManager.registerCommand(new net.xilla.discordcore.commandsystem.cmd.Staff());

        Log.sendMessage(3, "Loading Staff Manager...");
        this.staffManager = new StaffManager();

        Log.sendMessage(3, "Loading Staff Config Defaults...");
        Config staff = configManager.getConfig("staff.json");

        Staff admin = new Staff("Admin", 10, "example");
        Staff mod = new Staff("Mod", 5, "example");
        staff.loadDefault(admin.getKey(), admin.toJson());
        staff.loadDefault(mod.getKey(), mod.toJson());
        staffManager.addStaff(mod);
        staffManager.addStaff(admin);
        staff.save();

        // Tries to start the bot and
        Log.sendMessage(3, "Loading Discord Bot...");
        try {
            String token = configManager.getConfig("settings.json").getString("botToken");
            this.bot = new JDABuilder(token).build();
            this.bot.addEventListener(new CommandDiscord());
        } catch (Exception ex) {
            Log.sendMessage(2, "Your discord bot's token is invalid, or could not connect!");
            ex.printStackTrace();
            System.exit(0);
        }

        // Loading Modules
        Log.sendMessage(3, "Loading Modules Folders...");
        File folder = new File("modules/"); // Initializes the file for the folder
        if (!folder.exists()) { // Checks if the folder does not exist
            folder.mkdirs();  // Creates the folder
        }

        Log.sendMessage(3, "Loading Installer Manager...");
        this.installerManager = new InstallerManager();

        Log.sendMessage(3, "Loading Installer Options For The Core...");
        SettingObject botTokenSetting = new SettingObject("botToken", "Input your bot token : ");
        SettingObject guildIDSetting = new SettingObject("guildID", "Input your guild's ID (Enable developer options in discord) : ");
        SettingObject companyNameSetting = new SettingObject("companyName", "Input your company's name : ");
        InstallerObject installerObject = new InstallerObject("Core", settings, botTokenSetting, guildIDSetting, companyNameSetting);
        installerManager.addInstaller(installerObject);

        Log.sendMessage(3, "Loading Modules Manager...");
        this.moduleManager = new ModuleManager(); // Initializes the module manager and loads all modules

        Log.sendMessage(3, "Running Installer...");
        installerManager.install();

        Log.sendMessage(3, "Starting Message Event Handler...");
        this.messageEventManger = new MessageEventManger(); // Initializes the message event manager.

        Log.sendMessage(3, "Loading Command Worker...");
        this.commandWorker = new CommandWorker(); // Initializes the command worker.
        Log.sendMessage(3, "Starting Command Worker...");
        commandWorker.start(); // Starts the command worker (command line)

        Log.sendMessage(0, "Finished loading " + new Data().getVersion() + " in " + ((System.currentTimeMillis() - start) / 1000.0) + "s!");

    }

    public InstallerManager getInstallerManager() {
        return installerManager;
    }

    public JDA getBot() {
        return bot;
    }

    public CommandWorker getCommandWorker() {
        return commandWorker;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public StaffManager getStaffManager() {
        return staffManager;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    public MessageEventManger getMessageEventManger() {
        return messageEventManger;
    }
}
