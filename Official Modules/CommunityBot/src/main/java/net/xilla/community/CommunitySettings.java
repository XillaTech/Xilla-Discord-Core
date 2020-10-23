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

        addDefault("economy-enabled", true);
        addDefault("giveaway-enabled", true);
        addDefault("invite-tracking-enabled", true);
        addDefault("welcome-enabled", true);
        addDefault("suggestions-enabled", true);
        addDefault("punishments-enabled", true);
        addDefault("reaction-roles-enabled", true);
        addDefault("reviews-enabled", false);
        addDefault("mod-mail-enabled", false);
        addDefault("suggestions-enabled", false);
        addDefault("welcome-enabled", true);
        addDefault("farewell-enabled", true);
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
