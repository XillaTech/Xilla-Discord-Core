package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.CommandExecutor;

public class CommandBuilder extends TobiasObject {

    private String module = null;
    private String name = null;
    private String[] activators = null;
    private String usage = null;
    private String description = null;
    private int staffLevel = -1;
    private CommandExecutor commandExecutor = null;

    public CommandBuilder CommandBuilder(String module, String name, int staffLevel) {
        this.module = module;
        this.name = name;
        this.staffLevel = staffLevel;
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

    public CommandBuilder setStaffLevel(int staffLevel) {
        this.staffLevel = staffLevel;
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
        if(staffLevel == -1) {
            this.staffLevel = 0;
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
        CoreCommand command = new CoreCommand(module, name, activators, usage, description, staffLevel, commandExecutor);

        getCommandManager().registerCommand(command);
    }

}
