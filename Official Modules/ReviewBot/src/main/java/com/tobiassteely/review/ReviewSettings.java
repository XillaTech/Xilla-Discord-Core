package com.tobiassteely.review;

import net.xilla.discordcore.settings.Settings;

public class ReviewSettings extends Settings {

    public ReviewSettings() {
        super("Review", "modules/review/review-settings.json");

        getConfig().setDefault("reviewChannelID", "id");

        getInstaller().install("Which channel should reviews be sent to?", "reviewChannelID", "id");

        getConfig().save();
    }

    public String getReviewChannel() {
        return getConfig().getString("reviewChannelID");
    }

}
