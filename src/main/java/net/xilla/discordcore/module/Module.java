package net.xilla.discordcore.module;

import net.xilla.core.library.config.ConfigManager;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.command.CommandManager;
import net.xilla.discordcore.core.permission.group.GroupManager;

public class Module extends ManagerObject {

    private String type;
    private String name;
    private String version;

    public Module(String type, String name, String version) {
        super(name, "Modules");
        this.type = type;
        this.name = name;
        this.version = version;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public DiscordCore getDiscordCore() {
        return DiscordCore.getInstance();
    }

    public Platform getPlatform() {
        return DiscordCore.getInstance().getPlatform();
    }

    public CommandManager getCommandManager() {
        return DiscordCore.getInstance().getCommandManager();
    }

    public GroupManager getGroupManager() {
        return DiscordCore.getInstance().getPlatform().getGroupManager();
    }

    public ConfigManager getConfigManager() {
        return ConfigManager.getInstance();
    }

    @Override
    public XillaJson getSerializedData() {
        return null;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }
}
