package net.xilla.discordcore.api.timer;

import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.api.config.ConfigManager;
import net.xilla.discordcore.api.manager.ManagerObject;
import net.xilla.discordcore.api.manager.ManagerParent;
import org.json.simple.JSONObject;

import java.util.Map;

public class TimerManager extends ManagerParent {

    private static TimerManager instance;

    public static TimerManager getInstance() {
        return instance;
    }

    public TimerManager() {
        instance = this;
        reload();
    }


    @Override
    public void reload() {
        super.reload();

        Config config = ConfigManager.getInstance().getConfig("timers.json");
        JSONObject json = config.toJson();
        for(Object key : json.keySet()) {
            TimerObject timerObject = new TimerObject((Map<String, String>)json.get(key));
            addTimer(timerObject);
        }
    }

    public void save() {
        Config config = ConfigManager.getInstance().getConfig("timers.json");
        config.clear();
        for(ManagerObject object : getList()) {
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
