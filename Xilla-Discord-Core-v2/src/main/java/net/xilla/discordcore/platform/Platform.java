package net.xilla.discordcore.platform;

import net.xilla.discordcore.command.CommandManager;
import net.xilla.discordcore.command.type.template.TemplateManager;
import net.xilla.discordcore.module.ModuleManager;
import net.xilla.discordcore.module.cmd.ModulesCommand;
import net.xilla.discordcore.module.type.JavaModule;
import net.xilla.discordcore.module.type.PythonModule;
import net.xilla.discordcore.platform.cmd.HelpCommand;
import net.xilla.discordcore.staff.StaffManager;
import net.xilla.discordcore.staff.cmd.StaffCommand;

import java.util.concurrent.ConcurrentHashMap;

public class Platform {

    private String type;
    private CommandManager commandManager;
    private StaffManager staffManager;

    public Platform(String type) {
        this.type = type;
        this.commandManager = new CommandManager();
        this.staffManager = new StaffManager();

        // Registers the commands for the core
        this.commandManager.registerCommand(new HelpCommand());
        this.commandManager.registerCommand(new ModulesCommand());
        this.commandManager.registerCommand(new StaffCommand());
    }

    public String getType() {
        return type;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public StaffManager getStaffManager() {
        return staffManager;
    }

    public enum getPlatform {
        // Available platforms
        BUNGEE("BUNGEE"),
        SPIGOT("SPIGOT"),
        STANDALONE("STANDALONE");

        public String name;

        getPlatform(String str) {
            this.name = str;
        }
    }

}
