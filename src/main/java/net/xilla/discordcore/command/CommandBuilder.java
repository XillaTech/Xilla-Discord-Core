package net.xilla.discordcore.command;

import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.library.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.command.Command;
import net.xilla.discordcore.core.command.CommandExecutor;

import java.util.Collections;

public class CommandBuilder implements CoreObject {

    private String module = null;
    private String name = null;
    private String[] activators = null;
    private String usage = null;
    private String description = null;
    private String permission = null;
    private CommandExecutor commandExecutor = null;
    private boolean consoleSupported;

    public CommandBuilder(String module, String name, boolean consoleSupported) {
        this.module = module;
        this.name = name;
        this.consoleSupported = consoleSupported;
    }

    public CommandBuilder(String module, String name) {
        this(module, name, false);
    }

    public CommandBuilder CommandBuilder() {
        return this;
    }

    public CommandBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CommandBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        return this;
    }

    public CommandBuilder setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public CommandBuilder setModule(String module) {
        this.module = module;
        return this;
    }

    public CommandBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandBuilder setActivators(String... activators) {
        this.activators = activators;
        return this;
    }

    public void build() {
        if(module == null) {
            Logger.log(LogLevel.ERROR, "Could not build command... Missing module information.", CommandBuilder.class);
            return;
        }
        if(name == null) {
            Logger.log(LogLevel.ERROR, "Could not build command... Missing name information.", CommandBuilder.class);
            return;
        }
        if(commandExecutor == null) {
            Logger.log(LogLevel.ERROR, "Could not build command... Missing command executor.", CommandBuilder.class);
            return;
        }
        if(activators == null) {
            this.activators = new String[] {name.toLowerCase()};
        }
        if(usage == null) {
            this.usage = name.toLowerCase();
        }
        if(description == null) {
            this.description = "Command belongs to module: " + module;
        }
        Command command = new Command(module, name, activators, usage, description, permission, Collections.singletonList(commandExecutor), consoleSupported);

        DiscordCore.getInstance().getCommandManager().put(command);
    }

}
