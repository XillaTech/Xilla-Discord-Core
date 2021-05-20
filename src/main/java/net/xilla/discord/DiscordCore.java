package net.xilla.discord;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.xilla.core.library.Pair;
import net.xilla.core.library.program.ProgramManager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.core.reflection.storage.StorageReflectionManager;
import net.xilla.discord.api.DiscordAPI;
import net.xilla.discord.api.command.CommandProcessor;
import net.xilla.discord.api.command.console.ConsoleProcessor;
import net.xilla.discord.api.embed.EmbedProcessor;
import net.xilla.discord.api.permission.GroupProcessor;
import net.xilla.discord.api.permission.UserProcessor;
import net.xilla.discord.api.placeholder.PlaceholderProcessor;
import net.xilla.discord.listener.CommandListener;
import net.xilla.discord.manager.command.console.ConsoleManager;
import net.xilla.discord.manager.command.discord.CommandManager;
import net.xilla.discord.manager.command.discord.cmd.DiscordHelp;
import net.xilla.discord.manager.command.discord.cmd.PermissionCommand;
import net.xilla.discord.manager.embed.EmbedManager;
import net.xilla.discord.manager.embed.template.HelpTemplate;
import net.xilla.discord.manager.permission.group.GroupManager;
import net.xilla.discord.manager.permission.user.UserManager;
import net.xilla.discord.manager.placeholder.PlaceholderManager;
import net.xilla.discord.manager.placeholder.object.HelpPlaceholder;
import net.xilla.discord.reflection.EmbedBuilderReflection;
import net.xilla.discord.reflection.GuildReflection;
import net.xilla.discord.reflection.MemberReflection;
import net.xilla.discord.reflection.UserReflection;
import net.xilla.discord.setting.DiscordSettings;
import net.xilla.discord.setting.ProgramSettings;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class DiscordCore extends ProgramManager implements DiscordAPI {

    public static void main(String[] args) {
        DiscordCore.startBot();
    }

    public static DiscordCore startBot() {
        Pair<DiscordCore, ProgramSettings> data = createBot();
        startBot(data.getValueOne(), data.getValueTwo());

        return data.getValueOne();
    }

    public static Pair<DiscordCore, ProgramSettings> createBot() {
        ProgramSettings settings = new ProgramSettings();
        DiscordCore core = new DiscordCore(settings.getProgramName());

        checkToken(settings);

        return new Pair<>(core, settings);
    }

    public static void checkToken(ProgramSettings settings) {
        if(settings.getDiscordToken().equalsIgnoreCase("none")) {
            Scanner scanner = new Scanner(System.in);
            Logger.log(LogLevel.INFO, "You must setup a discord token before the bot can start!", DiscordCore.class);

            System.out.print("Discord Token: ");
            settings.setDiscordToken(scanner.nextLine());
            settings.save();
            Logger.log(LogLevel.INFO, "Thank you, the bot will start with the provided token!", DiscordCore.class);
        }
    }

    public static void startBot(DiscordCore core, ProgramSettings settings) {
        try {
            core.start(JDABuilder.createDefault(settings.getDiscordToken()));
        } catch (LoginException loginException) {
            Logger.log(LogLevel.FATAL, "Failed to start the bot due to an invalid bot token!", DiscordCore.class);
            Logger.log(LogLevel.FATAL, loginException, DiscordCore.class);
        } catch (InterruptedException interruptedException) {
            Logger.log(LogLevel.FATAL, "Failed to start bot.", DiscordCore.class);
            Logger.log(LogLevel.FATAL, interruptedException, DiscordCore.class);
        }
    }

    @Getter
    private static DiscordCore instance;

    @Getter
    private JDA jda = null;

    public DiscordCore(String name) {
        super(name);

        instance = this;
    }

    public boolean start(JDABuilder jdaBuilder) throws LoginException, InterruptedException {

        if(this.jda != null) {
            return false;
        }

        Logger.log(LogLevel.INFO, "Loading JDA reflection...", getClass());
        loadReflection();

        Logger.log(LogLevel.INFO, "Connecting to discord...", getClass());
        this.jda = jdaBuilder.build();
        this.jda.awaitReady();

        Logger.log(LogLevel.INFO, "Loading core settings...", getClass());
        loadSettings();

        Logger.log(LogLevel.INFO, "Loading core managers...", getClass());
        loadManagers();

        Logger.log(LogLevel.INFO, "Loading core listeners...", getClass());
        loadListeners();

        Logger.log(LogLevel.INFO, "Loading core commands...", getClass());
        loadCommands();

        Logger.log(LogLevel.INFO, "Loading placeholders...", getClass());
        loadPlaceholders();

        startup();

        Logger.log(LogLevel.INFO, "The core is successfully loaded", getClass());
        return true;
    }

    private void loadReflection() {
        StorageReflectionManager.getInstance().put(new GuildReflection());
        StorageReflectionManager.getInstance().put(new UserReflection());
        StorageReflectionManager.getInstance().put(new MemberReflection());
        StorageReflectionManager.getInstance().put(new EmbedBuilderReflection());
    }

    private void loadManagers() {
        registerManager(new CommandManager());
        registerManager(new ConsoleManager());
        registerManager(new UserManager());
        registerManager(new GroupManager());
        registerManager(new PlaceholderManager());
        registerManager(new EmbedManager());
    }

    private void loadSettings() {
        registerSettings(new DiscordSettings());
    }

    private void loadListeners() {
        jda.addEventListener(new CommandListener());
    }

    private void loadCommands() {
        getCommandProcessor().putObject(new DiscordHelp());
        getCommandProcessor().putObject(new PermissionCommand());
    }

    private void loadPlaceholders() {
        getPlaceholderProcessor().putObject(new HelpPlaceholder());
    }

    @Override
    public CommandProcessor getCommandProcessor() {
        return getController().getManager(CommandManager.class);
    }

    @Override
    public ConsoleProcessor getConsoleProcessor() {
        return getController().getManager(ConsoleManager.class);
    }

    @Override
    public GroupProcessor getGroupProcessor() {
        return getController().getManager(GroupManager.class);
    }

    @Override
    public UserProcessor getUserProcessor() {
        return getController().getManager(UserManager.class);
    }

    @Override
    public PlaceholderProcessor getPlaceholderProcessor() {
        return getController().getManager(PlaceholderManager.class);
    }

    @Override
    public EmbedProcessor getEmbedProcessor() {
        return getController().getManager(EmbedManager.class);
    }

    public DiscordSettings getSettings() {
        return getController().getSettings(DiscordSettings.class);
    }

}
