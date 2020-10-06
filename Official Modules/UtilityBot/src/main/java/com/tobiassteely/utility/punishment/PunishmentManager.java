package com.tobiassteely.utility.punishment;

import net.xilla.core.library.manager.Manager;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class PunishmentManager extends Manager<Punishment> {

    public static Map<String, List<Punishment>> punishments = new HashMap<>();
    public static Map<String, List<Punishment>> mutes = new HashMap<>();

    public PunishmentManager() {
        super("UTB.Punishment", "modules/Utility/punishments.json");
    }

    @Override
    protected void load() {
        for(Object obj : getData().values()) {
            put(new Punishment((JSONObject)obj));
        }
    }

    @Override
    protected void objectAdded(Punishment punishment) {
        if(!punishments.containsKey(punishment.getUserID())) {
            punishments.put(punishment.getUserID(), new Vector<>());
        }
        punishments.get(punishment.getUserID()).add(punishment);

        if(punishment.getType().equalsIgnoreCase("mute")) {
            if (!mutes.containsKey(punishment.getUserID())) {
                mutes.put(punishment.getUserID(), new Vector<>());
            }
            mutes.get(punishment.getUserID()).add(punishment);
        }
    }

    @Override
    protected void objectRemoved(Punishment punishment) {
        punishments.get(punishment.getUserID()).remove(punishment);
        mutes.get(punishment.getUserID()).remove(punishment);
    }

}
