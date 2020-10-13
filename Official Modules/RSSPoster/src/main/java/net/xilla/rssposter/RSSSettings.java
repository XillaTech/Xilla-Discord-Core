package net.xilla.rssposter;

import net.xilla.discordcore.settings.Settings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RSSSettings extends Settings {

    public RSSSettings() {
        super("RSS", "rss.json");

        JSONObject json = new JSONObject();
        json.put("link", "https://usafreedom.live/feed");
        json.put("name", "USAFreedomLIVE");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(json);

        getConfig().setDefault("feeds", jsonArray);
        getConfig().setDefault("refresh-time", 10);
        getConfig().setDefault("max-length", 256);

        getConfig().save();
    }

    public JSONArray getFeeds() {
        return getConfig().get("feeds");
    }

    public int getRefreshTime() {
        return getConfig().getInt("refresh-time");
    }

    public int getMaxLength() {
        return getConfig().getInt("max-length");
    }

}
