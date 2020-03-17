package net.xilla.discordcore.api.timer;

import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.api.config.ConfigManager;
import net.xilla.discordcore.api.manager.ManagerObject;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TimerObject extends ManagerObject {

    private long start;
    private long time;

    public TimerObject(String id, long start, long time) {
        super(id, new Config("timers.json"));
        this.start = start;
        this.time = time;
    }

    public TimerObject(Map<String, String> map) {
        super(map.get("id"), ConfigManager.getInstance().getConfig("timers.json"));
        this.start = Long.parseLong(map.get("start"));
        this.time = Long.parseLong(map.get("time"));
    }

    public boolean isActive() {
        return (System.currentTimeMillis() - start) < time; // if within time period, return true
    }

    @Override
    public JSONObject toJson() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", getKey());
        map.put("start", "" + start);
        map.put("time", "" + time);
        return new JSONObject(map);
    }
}
