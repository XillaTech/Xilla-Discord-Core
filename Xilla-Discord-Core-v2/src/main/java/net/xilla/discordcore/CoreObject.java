package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import net.dv8tion.jda.api.JDA;
import net.xilla.discordcore.api.module.ModuleManager;
import net.xilla.discordcore.platform.CoreSettings;
import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.staff.StaffManager;

public class CoreObject extends TobiasObject {

    public DiscordCore getDiscordCore() {
        return DiscordCore.getInstance();
    }

    public StaffManager getStaffManager() {
        return DiscordCore.getInstance().getStaffManager();
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

}
