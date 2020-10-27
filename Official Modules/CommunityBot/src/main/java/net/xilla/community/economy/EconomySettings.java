package net.xilla.community.economy;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.community.giveaway.GiveawaySettings;
import net.xilla.discordcore.settings.GuildSettings;

public class EconomySettings extends GuildSettings {

    private static EconomySettings instance = new EconomySettings();

    public static EconomySettings getInstance() {
        return instance;
    }

    public EconomySettings() {
        super("Economy", "modules/Economy/settings/");

        setDefault("work-enabled", true);
        setDefault("daily-enabled", true);
        setDefault("weekly-enabled", true);
        setDefault("monthly-enabled", true);
        setDefault("messages-enabled", true);
        setDefault("label", "Credits");
        setDefault("work-payout", 5);
        setDefault("daily-payout", 10);
        setDefault("weekly-payout", 25);
        setDefault("monthly-payout", 50);
        setDefault("messages-payout", 2);
        setDefault("messages-delay-seconds", 10);
        setDefault("work-delay-minutes", 360);
    }

    public boolean isWorkEnabled(Guild guild) {
        return get(guild, "work-enabled");
    }

    public boolean isDailyEnabled(Guild guild) {
        return get(guild, "work-enabled");
    }

    public boolean isWeeklyEnabled(Guild guild) {
        return get(guild, "work-enabled");
    }

    public boolean isMonthlyEnabled(Guild guild) {
        return get(guild, "work-enabled");
    }

    public int workDelayMinutes(Guild guild) {
        return Integer.parseInt(get(guild, "work-delay-minutes"));
    }

    public int messagesDelaySeconds(Guild guild) {
        return Integer.parseInt(get(guild, "messages-delay-seconds"));
    }

    public String getLabel(Guild guild) {
        return get(guild, "label");
    }

    public int getWorkPayout(Guild guild) {
        return get(guild, "work-payout");
    }

    public int getDailyPayout(Guild guild) {
        return get(guild, "daily-payout");
    }

    public int getWeeklyPayout(Guild guild) {
        return get(guild, "weekly-payout");
    }

    public int getMonthlyPayout(Guild guild) {
        return get(guild, "monthly-payout");
    }

    public int getMessagesPayout(Guild guild) {
        return get(guild, "messages-payout");
    }

}
