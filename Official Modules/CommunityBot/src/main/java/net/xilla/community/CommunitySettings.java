package net.xilla.community;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.settings.GuildSettings;

public class CommunitySettings extends GuildSettings {

    private static CommunitySettings instance = new CommunitySettings();

    public static CommunitySettings getInstance() {
        return instance;
    }

    public CommunitySettings() {
        super("Community", "modules/Community/settings/");

        setDefault("economy-enabled", true);
        setDefault("giveaway-enabled", true);
        setDefault("invite-tracking-enabled", true);
        setDefault("welcome-enabled", true);
        setDefault("suggestions-enabled", true);
        setDefault("punishments-enabled", true);
        setDefault("reaction-roles-enabled", true);
        setDefault("reviews-enabled", false);
        setDefault("mod-mail-enabled", false);
        setDefault("suggestions-enabled", false);
        setDefault("welcome-enabled", true);
        setDefault("farewell-enabled", true);
    }

    public boolean isEconomyEnabled(String guildID) {
        return get(guildID, "economy-enabled");
    }

    public boolean isGiveawayEnabled(String guildID) {
        return get(guildID, "giveaway-enabled");
    }

    public boolean isInviteTrackingEnabled(String guildID) {
        return get(guildID, "invite-tracking-enabled");
    }

    public boolean isWelcomeEnabled(String guildID) {
        return get(guildID, "welcome-enabled");
    }

    public boolean isPunishmentsEnabled(String guildID) {
        return get(guildID, "punishments-enabled");
    }

    public boolean isReactionRolesEnabled(String guildID) {
        return get(guildID, "reaction-roles-enabled");
    }

    public boolean isReviewsEnabled(String guildID) {
        return get(guildID, "reviews-enabled");
    }

    public boolean isModMailEnabled(String guildID) {
        return get(guildID, "mod-mail-enabled");
    }

    public boolean isSuggestionsEnabled(String guildID) {
        return get(guildID, "suggestions-enabled");
    }

}
