package net.xilla.discordcore.core;

import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.settings.Settings;

public class CoreSettings extends Settings {

    public CoreSettings() {
        super("Core", "settings.json");

        // Loads settings
        if(DiscordCore.getInstance().getType().equals(Platform.getPlatform.STANDALONE.name) || DiscordCore.getInstance().getType().equals(Platform.getPlatform.EMBEDDED.name)) {
            // Fancy installer for standalone
            getInstaller().install("The discord bot's token from https://discord.com/developers/", "token", "blank");
            getInstaller().install("The discord bot's nickname / public name", "bot-name", "blank");
            getInstaller().install("The discord bot's embed coloring (#000000)", "embed-color", "blank");
            getInstaller().install("The prefix for all commands (-)", "command-prefix", "blank");
            getInstaller().install("The bot's current activity or game being played", "activity", "blank");
        }

        // Discord sharding doesn't need an installer as you only need it for bots in >2,500 servers
        getConfig().loadDefault("shards", 1);
        getConfig().loadDefault("token", "bottoken");
        getConfig().loadDefault("bot-name", "Sever Name");
        getConfig().loadDefault("embed-color", "#42daf5");
        getConfig().loadDefault("command-prefix", "-");
        getConfig().loadDefault("activity", "mc.serverip.com");
        getConfig().loadDefault("respect-discord-admin", true);
        getConfig().loadDefault("clear-old-guilds", false);
        getConfig().loadDefault("clear-old-guild-time", 86400);
        getConfig().loadDefault("check-time", 60);
        getConfig().loadDefault("last-check-time", -1);
        getConfig().loadDefault("minimize-help", false);
        getConfig().save();
    }

    public String getBotToken() {
        return getConfig().getString("token");
    }

    public String getBotName() {
        return getConfig().getString("bot-name");
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

    public Boolean isMinimizeHelp() {
        return getConfig().getBoolean("minimize-help");
    }

    public Boolean isRespectDiscordAdmin() {
        return getConfig().getBoolean("respect-discord-admin");
    }

    public Boolean isClearOldGuilds() {
        return getConfig().getBoolean("clear-old-guilds");
    }

    public int getClearOldGuildTime() {
        return getConfig().getInt("clear-old-guild-time");
    }

    public int getCheckTime() {
        return getConfig().getInt("check-time");
    }

    public long getLastCheckTime() {
        return getConfig().getLong("last-check-time");
    }

    public void setLastCheckTime(long time) {
        getConfig().set("last-check-time", time);
        getConfig().save();
    }

}
