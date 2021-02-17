package net.xilla.discordcore.core;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.ServerSettings;
import net.xilla.discordcore.command.cmd.*;
import net.xilla.discordcore.command.template.TemplateManager;
import net.xilla.discordcore.core.command.response.CoreCommandResponder;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.permission.group.GroupManager;
import net.xilla.discordcore.core.permission.user.UserManager;
import net.xilla.discordcore.core.server.ServerManager;
import net.xilla.discordcore.library.embed.EmbedManager;
import net.xilla.discordcore.library.form.form.FormManager;
import net.xilla.discordcore.library.program.ProgramInterface;
import net.xilla.discordcore.settings.GuildSettingsManager;

public class Platform implements ProgramInterface {

    @Getter
    private static Platform instance;

    @Setter
    @Getter
    private String type;

    @Getter
    private GroupManager groupManager;

    @Getter
    private UserManager userManager;

    @Getter
    private TemplateManager templateManager;

    @Getter
    private ServerManager serverManager;

    @Getter
    private CoreWorker coreWorker;

    @Getter
    private FormManager formManager;

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
     * The manager used to manage, edit, and control embeds.
     */
    @Getter
    private EmbedManager embedManager;

    public Platform(String type) {

        instance = this;

        this.type = type;
        this.groupManager = new GroupManager();
        this.userManager = new UserManager();
        this.coreWorker = new CoreWorker();
        this.serverManager = new ServerManager();
        this.formManager = new FormManager();
        this.guildSettingsManager = new GuildSettingsManager();
        this.serverSettings = new ServerSettings();
        this.embedManager = new EmbedManager();

        DiscordCore.getInstance().getCommandManager().setCommandRunCheck(new CommandCheck());
        DiscordCore.getInstance().getCommandManager().setResponder(new CoreCommandResponder());

        this.templateManager = new TemplateManager();

        new ModulesCommand();
        new GroupManagerCommand();
        new UserManagerCommand();
        new HelpCommand();
        new EndCommand();
        new RestartCommand();
        new TemplateCommand();
        new CoreSettingsCommand();
        new SettingsCommand();
        new EmbedCommand();
        new CoreCommands();
        new TestCommand();

        DiscordCore.getInstance().getCommandManager().setPermissionError((args, data) -> {
            EmbedBuilder builder = new EmbedBuilder();

            if(data.get() instanceof MessageReceivedEvent) {
                builder = getEmbed((MessageReceivedEvent)data.get());
            }

            builder.setTitle("Error!");
            builder.setDescription("You do not have permission for that command!");

            return new CoreCommandResponse(data).setEmbed(builder.build());
        });
    }

    public enum getPlatform {
        // Available platforms
        BUNGEE("BUNGEE"),
        SPIGOT("SPIGOT"),
        STANDALONE("STANDALONE"),
        EMBEDDED("EMBEDDED");

        public String name;

        getPlatform(String str) {
            this.name = str;
        }
    }

}
