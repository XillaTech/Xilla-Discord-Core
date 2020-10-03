package com.tobiassteely.utility.suggestion;

import net.xilla.discordcore.settings.Settings;

public class SuggestionSettings extends Settings {

    public SuggestionSettings() {
        super("Suggestions", "modules/Utility/suggestion-settings.json");

        getConfig().loadDefault("channelID", "id");

        getInstaller().install("The channel where suggestions are posted.", "channelID", "id");

        getConfig().save();
    }

    public String getChannelID() {
        return getConfig().getString("channelID");
    }

}
