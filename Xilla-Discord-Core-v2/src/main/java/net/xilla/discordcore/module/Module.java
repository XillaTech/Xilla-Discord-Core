package net.xilla.discordcore.module;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.api.config.ConfigManager;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandManager;
import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.staff.StaffManager;

public class Module extends ManagerObject {

    private String type;
    private String name;
    private String version;

    public Module(String type, String name, String version) {
        super(name);
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

    public TobiasAPI getTobiasAPI() {
        return DiscordCore.getInstance().getTobiasAPI();
    }

    public CommandManager getCommandManager() {
        return DiscordCore.getInstance().getPlatform().getCommandManager();
    }

    public StaffManager getStaffManager() {
        return DiscordCore.getInstance().getPlatform().getStaffManager();
    }

    public ConfigManager getConfigManager() {
        return DiscordCore.getInstance().getTobiasAPI().getConfigManager();
    }

}
