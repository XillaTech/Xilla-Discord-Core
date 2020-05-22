package net.xilla.discordcore.installer;

import net.xilla.discordcore.api.Log;
import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.api.manager.ManagerObject;

import java.util.Scanner;

public class InstallerObject extends ManagerObject {

    private SettingObject[] settings;

    public InstallerObject(String module, Config config, SettingObject... settings) {
        super(module, config);
        this.settings = settings;
    }

    public SettingObject[] getSettings() {
        return settings;
    }

    public boolean install() {
        Config config = getConfig();
        boolean installed = false;
        Scanner scanner = new Scanner(System.in);
        for(SettingObject settingObject : settings) {
            if(config.get(settingObject.getSetting()) != null) {
                if(config.getString(settingObject.getSetting()).equalsIgnoreCase("example")) {
                    Log.sendMessage(0, settingObject.getDescription());
                    String userInput = scanner.nextLine();
                    config.set(settingObject.getSetting(), userInput);
                    installed = true;
                }
            }
        }
        if(installed)
            config.save();

        return installed;
    }

}
