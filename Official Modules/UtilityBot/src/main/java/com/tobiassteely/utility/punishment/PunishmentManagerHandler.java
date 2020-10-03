package com.tobiassteely.utility.punishment;

import com.tobiassteely.tobiasapi.api.manager.ManagerEventExecutor;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import org.json.simple.JSONObject;

import java.util.*;

public class PunishmentManagerHandler implements ManagerEventExecutor<Punishment> {

    public static Map<String, List<Punishment>> punishments = new HashMap<>();
    public static Map<String, List<Punishment>> mutes = new HashMap<>();

    @Override
    public Punishment loadObject(JSONObject json) {

        String id = json.get("id").toString();
        String staffID = json.get("staffID").toString();
        String type = json.get("type").toString();
        String userID = json.get("userID").toString();
        String guildID = json.get("guildID").toString();
        String description = json.get("description").toString();
        long duration = Long.parseLong(json.get("duration").toString());
        long startTime = Long.parseLong(json.get("startTime").toString());

        // String id, String staffID, String userID, String type, long startTime, long duration
        Punishment punishment = new Punishment(id, staffID, userID, type, guildID, description, startTime, duration);
        return punishment;
    }

    @Override
    public JSONObject saveObject(Punishment object) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", object.getKey());
        jsonObject.put("duration", object.getDuration());
        jsonObject.put("staffID", object.getStaffID());
        jsonObject.put("startTime", object.getStartTime());
        jsonObject.put("guildID", object.getGuildID());
        jsonObject.put("description", object.getDescription());
        jsonObject.put("type", object.getType());
        jsonObject.put("userID", object.getUserID());

        return jsonObject;
    }

    @Override
    public void onObjectAdd(ManagerParent<Punishment> managerParent, Punishment punishment) {
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
    public void onObjectRemove(ManagerParent<Punishment> managerParent, String s, Punishment punishment) {
        mutes.get(punishment.getUserID()).remove(punishment);
    }

    @Override
    public void onReload(ManagerParent<Punishment> managerParent) {

    }

    @Override
    public void onLoad(ManagerParent<Punishment> managerParent) {

    }

    @Override
    public void onUnload(ManagerParent<Punishment> managerParent) {

    }

    @Override
    public void onSave(ManagerParent<Punishment> managerParent, JSONObject jsonObject) {

    }
}
