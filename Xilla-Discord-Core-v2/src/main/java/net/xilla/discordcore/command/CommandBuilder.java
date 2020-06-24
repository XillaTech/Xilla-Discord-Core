package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;

import java.util.Collections;

public class CommandBuilder extends TobiasObject {

    private String module = null;
    private String name = null;
    private String[] activators = null;
    private String usage = null;
    private String description = null;
    private CommandExecutor commandExecutor = null;

    public CommandBuilder CommandBuilder(String module, String name) {
        this.module = module;
        this.name = name;
        return this;
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

    public CommandBuilder setActivators(String... activators) {
        this.activators = activators;
        return this;
    }

    public void build() {
        if(module == null) {
            getLog().sendMessage(2, "Could not build command... Missing module information.");
            return;
        }
        if(name == null) {
            getLog().sendMessage(2, "Could not build command... Missing name information.");
            return;
        }
        if(commandExecutor == null) {
            getLog().sendMessage(2, "Could not build command... Missing command executor.");
            return;
        }
        if(activators == null) {
            this.activators = new String[] {name.toLowerCase()};
        }
        if(usage == null) {
            this.usage = name.toLowerCase();
        }
        if(description == null) {
            this.usage = "Command belongs to module: " + module;
        }
        Command command = new Command(module, name, activators, usage, description, Collections.singletonList(commandExecutor));

        getCommandManager().registerCommand(command);
    }

}
