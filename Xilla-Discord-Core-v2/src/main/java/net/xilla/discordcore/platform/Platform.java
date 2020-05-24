package net.xilla.discordcore.platform;

import net.xilla.discordcore.command.CommandManager;
import net.xilla.discordcore.command.type.template.TemplateManager;
import net.xilla.discordcore.module.ModuleManager;
import net.xilla.discordcore.module.type.JavaModule;
import net.xilla.discordcore.module.type.PythonModule;
import net.xilla.discordcore.staff.StaffManager;

import java.util.concurrent.ConcurrentHashMap;

public class Platform {

    private String type;
    private CommandManager commandManager;
    private StaffManager staffManager;
    private ModuleManager moduleManager;

    public Platform(String type) {
        this.type = type;
        this.commandManager = new CommandManager();
        this.staffManager = new StaffManager();
        this.moduleManager = new ModuleManager();
    }

    public String getType() {
        return type;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public StaffManager getStaffManager() {
        return staffManager;
    }

    public enum getPlatform {
//        BUNGEE("BUNGEE"),
//        SPIGOT("SPIGOT"),
        STANDALONE("STANDALONE");

        private String platform;

        getPlatform(String str) {
            this.platform = str;
        }

        public String getPlatform() {
            return platform;
        }
    }

}
