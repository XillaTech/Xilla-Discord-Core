package net.xilla.discordcore.api.timer;

import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.api.config.ConfigManager;
import net.xilla.discordcore.api.manager.ManagerObject;
import net.xilla.discordcore.api.manager.ManagerParent;
import org.json.simple.JSONObject;

import java.util.Map;

public class TimerManager extends ManagerParent {

    public TimerManager() {
        reload();
    }


    @Override
    public void reload() {
        super.reload();

        Config config = DiscordCore.getInstance().getConfigManager().getConfig("timers.json");
        JSONObject json = config.toJson();
        for(Object key : json.keySet()) {
            TimerObject timerObject = new TimerObject((Map<String, String>)json.get(key));
            addTimer(timerObject);
        }
    }

    public void save() {
        Config config = DiscordCore.getInstance().getConfigManager().getConfig("timers.json");
        config.clear();
        for(ManagerObject object : getVector()) {
            TimerObject timerObject = (TimerObject)object;
            config.toJson().put(timerObject.getKey(), timerObject.toJson());
        }
        config.save();
    }

    public void addTimer(TimerObject timer) {
        super.addObject(timer);
        save();
    }

    public void removeTimer(TimerObject timer) {
        super.removeObject(timer.getKey());
        save();
    }

}
