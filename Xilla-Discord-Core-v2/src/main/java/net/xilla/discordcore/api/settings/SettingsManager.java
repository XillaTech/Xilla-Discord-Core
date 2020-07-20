package net.xilla.discordcore.api.settings;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;

import java.util.Set;

public class SettingsManager extends ManagerParent {

    public SettingsManager() {
        super(true);
    }

    public void addSettings(Settings settings) {
        addObject(settings);
    }

    public Settings getSettings(String name) {
        return (Settings)getObject(name);
    }

    public Set<String> getSettingsNames() {
        return getCache("key").getCache().keySet();
    }

}
