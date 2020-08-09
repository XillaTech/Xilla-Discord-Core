package com.tobiassteely.utility.punishment;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import org.json.simple.JSONObject;

public class PunishmentManager extends ManagerParent<Punishment> {

    public PunishmentManager() {
        super(true, "modules/Utility/punishments.json");
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
    public boolean loadObject(JSONObject json) {

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
        addObject(punishment);

        return true;
    }

}
