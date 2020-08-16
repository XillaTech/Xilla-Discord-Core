package net.xilla.discordcore.api.settings;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;

import java.util.Set;

public class SettingsManager extends ManagerParent<Settings> {

    public SettingsManager() {
        super("XDC.Settings", true);
    }

    public void addSettings(Settings settings) {
        addObject(settings);
    }

    public Settings getSettings(String name) {
        return getObject(name);
    }

    public Set<String> getSettingsNames() {
        return getCache("key").getCache().keySet();
    }

}
