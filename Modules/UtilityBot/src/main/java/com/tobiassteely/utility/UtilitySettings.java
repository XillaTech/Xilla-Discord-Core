package com.tobiassteely.utility;

import net.xilla.discordcore.settings.Settings;

public class UtilitySettings extends Settings {

    public UtilitySettings() {
        super("Utility", "modules/Utility/utility-settings.json");

        getConfig().loadDefault("polls-feature", true);
        getConfig().loadDefault("suggestions-feature", true);
        getConfig().loadDefault("punishments-feature", true);
        getConfig().loadDefault("info-commands-feature", true);
        getConfig().save();
    }

    public boolean isPollsEnabled() {
        return getConfig().getBoolean("polls-feature");
    }

    public boolean isSuggestionsEnabled() {
        return getConfig().getBoolean("suggestions-feature");
    }

    public boolean isPunishmentsEnabled() {
        return getConfig().getBoolean("punishments-feature");
    }


    public boolean isInfoCommandsEnabled() {
        return getConfig().getBoolean("info-commands-feature");
    }

}
