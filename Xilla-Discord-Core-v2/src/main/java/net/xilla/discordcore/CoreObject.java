package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import net.dv8tion.jda.api.JDA;
import net.xilla.discordcore.api.module.ModuleManager;
import net.xilla.discordcore.command.CommandSettings;
import net.xilla.discordcore.core.CoreSettings;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.staff.GroupManager;

public class CoreObject extends TobiasObject {

    public DiscordCore getDiscordCore() {
        return DiscordCore.getInstance();
    }

    public GroupManager getGroupManager() {
        return DiscordCore.getInstance().getGroupManager();
    }

    public ModuleManager getModuleManager() {
        return DiscordCore.getInstance().getModuleManager();
    }

    public CoreSettings getCoreSetting() {
        return DiscordCore.getInstance().getSettings();
    }

    public JDA getBot() {
        return DiscordCore.getInstance().getBot();
    }

    public Platform getPlatform() {
        return DiscordCore.getInstance().getPlatform();
    }

    public CommandSettings getCommandSettings() {
        return DiscordCore.getInstance().getCommandSettings();
    }

}
