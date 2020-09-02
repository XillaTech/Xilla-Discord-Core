package net.xilla.discordcore.core;

import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandWorker;
import net.xilla.discordcore.command.cmd.*;
import net.xilla.discordcore.command.response.CoreCommandResponder;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.command.template.TemplateManager;
import net.xilla.discordcore.core.server.ServerManager;
import net.xilla.discordcore.core.staff.GroupManager;

import java.awt.*;

public class Platform extends CoreObject {

    private String type;
    private GroupManager groupManager;
    private TemplateManager templateManager;
    private ServerManager serverManager;
    private CoreWorker coreWorker;
    private CommandWorker commandWorker;

    public Platform(String type) {
        this.type = type;
        this.groupManager = new GroupManager();
        this.coreWorker = new CoreWorker();
        this.serverManager = new ServerManager();

        getCommandManager().setResponder(new CoreCommandResponder());
        this.templateManager = new TemplateManager();

        new ModulesCommand();
        new StaffCommand();
        new HelpCommand();
        new EndCommand();
        new RestartCommand();
        new TemplateCommand();
        new SettingsCommand();
        new EmbedCommand();
        new CoreCommands();

        if(getCommandSettings().isRateLimit()) {
            this.commandWorker = new CommandWorker();
            commandWorker.start();
        }

        getCommandManager().setPermissionError((args, data) -> {
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
}
