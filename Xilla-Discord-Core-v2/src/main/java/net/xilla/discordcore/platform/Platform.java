package net.xilla.discordcore.platform;

import net.xilla.discordcore.command.CommandManager;
import net.xilla.discordcore.command.type.template.TemplateManager;
import net.xilla.discordcore.module.JavaModule;
import net.xilla.discordcore.module.PythonModule;
import net.xilla.discordcore.staff.StaffManager;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Platform {

    private String type;
    private ConcurrentHashMap<String, PythonModule> pythonModules;
    private ConcurrentHashMap<String, JavaModule> javaModules;
    private CommandManager commandManager;
    private StaffManager staffManager;

    public Platform(String type) {
        this.type = type;
        this.pythonModules = new ConcurrentHashMap<>();
        this.javaModules = new ConcurrentHashMap<>();
        this.commandManager = new CommandManager();
        this.staffManager = new StaffManager();
    }

    public String getType() {
        return type;
    }

    public JavaModule getJavaModule(String name) {
        return javaModules.get(name);
    }

    public PythonModule getPythonModule(String name) {
        return pythonModules.get(name);
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
