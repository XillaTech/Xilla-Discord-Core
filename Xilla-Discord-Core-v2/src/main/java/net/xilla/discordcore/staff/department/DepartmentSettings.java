package net.xilla.discordcore.staff.department;

import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.settings.Settings;

import java.nio.file.Files;

public class DepartmentSettings extends Settings {

    public DepartmentSettings(String department) {
        super(DiscordCore.getInstance().getApi().getConfigManager().getConfig("department" + System.getProperty("file.separator") + department + ".json"));
    }

}
