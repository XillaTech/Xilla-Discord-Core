package net.xilla.discordcore.core;

import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.cmd.*;
import net.xilla.discordcore.core.command.response.CoreCommandResponder;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.command.template.TemplateManager;
import net.xilla.discordcore.core.permission.user.UserManager;
import net.xilla.discordcore.core.server.ServerManager;
import net.xilla.discordcore.core.permission.group.GroupManager;

import java.awt.*;

public class Platform extends CoreObject {

    private String type;
    private GroupManager groupManager;
    private UserManager userManager;
    private TemplateManager templateManager;
    private ServerManager serverManager;
    private CoreWorker coreWorker;

    public Platform(String type) {
        this.type = type;
        this.groupManager = new GroupManager();
        this.userManager = new UserManager();
        this.coreWorker = new CoreWorker();
        this.serverManager = new ServerManager();

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

        DiscordCore.getInstance().getCommandManager().setPermissionError((args, data) -> {
            EmbedBuilder builder = new EmbedBuilder().setTitle("Error!");
            builder.setDescription("You do not have permission for that command!");
            builder.setColor(Color.decode(getCoreSetting().getEmbedColor()));

            return new CoreCommandResponse(data).setEmbed(builder.build());
        });
    }

    public CoreWorker getCoreWorker() {
        return coreWorker;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public String getType() {
        return type;
    }

    public GroupManager getGroupManager() {
        return groupManager;
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

    public TemplateManager getTemplateManager() {
        return templateManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
