package net.xilla.discordcore.core;

import net.xilla.core.log.LogLevel;
import net.xilla.discordcore.settings.DiscordSettings;

public class CoreSettings extends DiscordSettings {

    public CoreSettings() {
        super("discord-settings.json");

        getConfig().setDefault("shards", 1);
        getConfig().setDefault("embed-color", "#018ed1");
        getConfig().setDefault("command-prefix", "-");
        getConfig().setDefault("activity", "none");
        getConfig().setDefault("activity-type", "Playing");
        getConfig().setDefault("activity-stream", "https://www.youtube.com/watch?v=5IXQ6f6eMxQ");
        getConfig().setDefault("respect-discord-admin", true);
        getConfig().setDefault("clear-old-guilds", false);
        getConfig().setDefault("clear-old-guild-time", 86400);
        getConfig().setDefault("clear-old-users", false);
        getConfig().setDefault("check-time", 60);
        getConfig().setDefault("user-threads", 16);
        getConfig().setDefault("group-threads", 16);
        getConfig().setDefault("last-check-time", -1);
        getConfig().setDefault("minimize-help", false);
        getConfig().setDefault("token", "bottoken");
        getConfig().setDefault("log-level", LogLevel.INFO.name());
        getConfig().save();
    }

    public CoreSettings(String base) {
        this();
    }

    public String getLogLevel() {
        return getConfig().getString("log-level");
    }

    public String getBotToken() {
        return getConfig().getString("token");
    }

    public String getEmbedColor() {
        return getConfig().getString("embed-color");
    }

    public String getCommandPrefix() {
        return getConfig().getString("command-prefix");
    }

    public String getActivity() {
        return getConfig().getString("activity");
    }

    public String getActivityURL() {
        return getConfig().getString("activity-stream");
    }

    public String getActivityType() {
        return getConfig().getString("activity-type");
    }

    public int getShards() {
        try {
            return getConfig().getInt("shards");
        } catch (ClassCastException ex) {

            return (int)getConfig().getLong("shards");
        }
    }

    public void setBotToken(String key) {
        getConfig().set("token", key);
        getConfig().save();
    }

    public void setBotName(String key) {
        getConfig().set("bot-name", key);
        getConfig().save();
    }

    public void setEmbedColor(String key) {
        getConfig().set("embed-color", key);
        getConfig().save();
    }

    public void setCommandPrefix(String key) {
        getConfig().set("command-prefix", key);
        getConfig().save();
    }

    public boolean isMinimizeHelp() {
        return getConfig().getBoolean("minimize-help");
    }

    public boolean isRespectDiscordAdmin() {
        return getConfig().getBoolean("respect-discord-admin");
    }

    public boolean isClearOldGuilds() {
        return getConfig().getBoolean("clear-old-guilds");
    }

    public int getClearOldGuildTime() {
        return getConfig().getInt("clear-old-guild-time");
    }

    public int getCheckTime() {
        return getConfig().getInt("check-time");
    }

    public int getUserThreads() {
        return getConfig().getInt("user-threads");
    }

    public int getGroupThreads() {
        return getConfig().getInt("group-threads");
    }

    public long getLastCheckTime() {
        return getConfig().getLong("last-check-time");
    }

    public void setLastCheckTime(long time) {
        getConfig().set("last-check-time", time);
        getConfig().save();
    }

}
