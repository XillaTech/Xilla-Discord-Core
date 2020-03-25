package net.xilla.discordcore.installer;

public class SettingObject {

    private String setting;
    private String description;

    public SettingObject(String setting, String description) {
        this.setting = setting;
        this.description = description;
    }

    public String getSetting() {
        return setting;
    }

    public String getDescription() {
        return description;
    }
}
