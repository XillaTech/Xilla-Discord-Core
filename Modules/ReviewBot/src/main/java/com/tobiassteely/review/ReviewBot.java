package com.tobiassteely.review;

import net.xilla.discordcore.module.JavaModule;

public class ReviewBot extends JavaModule {

    public ReviewBot() {
        super("Reviews", "1.0.0");
    }

    private static ReviewBot instance;

    public static ReviewBot getInstance() {
        return instance;
    }

    private ReviewManager reviewManager;
    private ReviewSettings reviewSettings;

    @Override
    public void onEnable() {
        instance = this;
        this.reviewManager = new ReviewManager();
        this.reviewSettings = new ReviewSettings();
        new ReviewCommands();
    }

    public ReviewManager getReviewManager() {
        return reviewManager;
    }

    public ReviewSettings getReviewSettings() {
        return reviewSettings;
    }
}
