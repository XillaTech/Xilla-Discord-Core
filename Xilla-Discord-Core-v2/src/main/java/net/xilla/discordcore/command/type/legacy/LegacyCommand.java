package net.xilla.discordcore.command.type.legacy;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.api.config.ConfigManager;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandManager;
import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.staff.StaffManager;

public class LegacyCommand extends ManagerObject {

    private String[] activators;
    private String module;
    private String description;
    private String usage;
    private int staffLevel;

    public LegacyCommand(String name, String[] activators, String module, String description, String usage, int staffLevel) {
        super(name);
        this.activators = activators;
        this.module = module;
        this.description = description;
        this.usage = usage;
        this.staffLevel = staffLevel;
    }

    public String getName() {
        return getKey();
    }

    public String getDescription() {
        return description;
    }

    public String[] getActivators() {
        return activators;
    }

    public String getModule() {
        return module;
    }

    public int getStaffLevel() {
        return staffLevel;
    }

    public String getUsage() {
        return usage;
    }

    public void setActivators(String[] activators) {
        this.activators = activators;
    }

    public boolean run(String[] args, MessageReceivedEvent event) {
        return false; // OVERRIDE
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
