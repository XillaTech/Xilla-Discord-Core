package com.tobiassteely.utility.punishment;

import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import org.json.simple.JSONObject;

public class Punishment extends ManagerObject {

    private long startTime;
    private long duration;
    private String staffID;
    private String guildID;
    private String userID;
    private String type;
    private String description;

    public Punishment(String id, String staffID, String userID, String type, String guildID, String description, long startTime, long duration) {
        super(id, "UTB.Punishment");
        this.staffID = staffID;
        this.userID = userID;
        this.type = type;
        this.guildID = guildID;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Punishment(JSONObject json) {
        super(json.get("id").toString(), "UTB.Punishment");

        this.staffID = json.get("staffID").toString();
        this.type = json.get("type").toString();
        this.userID = json.get("userID").toString();
        this.guildID = json.get("guildID").toString();
        this.description = json.get("description").toString();
        this.duration = Long.parseLong(json.get("duration").toString());
        this.startTime = Long.parseLong(json.get("startTime").toString());
    }

    public String getStaffID() {
        return staffID;
    }

    public String getUserID() {
        return userID;
    }

    public String getDescription() {
        return description;
    }

    public long getDuration() {
        return duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getType() {
        return type;
    }

    public String getGuildID() {
        return guildID;
    }

    @Override
    public XillaJson getSerializedData() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", getKey());
        jsonObject.put("duration", getDuration());
        jsonObject.put("staffID", getStaffID());
        jsonObject.put("startTime", getStartTime());
        jsonObject.put("guildID", getGuildID());
        jsonObject.put("description", getDescription());
        jsonObject.put("type", getType());
        jsonObject.put("userID", getUserID());

        return new XillaJson(jsonObject);
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }
}

