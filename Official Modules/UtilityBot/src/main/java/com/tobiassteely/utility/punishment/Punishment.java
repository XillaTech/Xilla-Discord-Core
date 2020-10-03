package com.tobiassteely.utility.punishment;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;

public class Punishment extends ManagerObject {

    private long startTime;
    private long duration;
    private String staffID;
    private String guildID;
    private String userID;
    private String type;
    private String description;

    public Punishment(String id, String staffID, String userID, String type, String guildID, String description, long startTime, long duration) {
        super(id);
        this.staffID = staffID;
        this.userID = userID;
        this.type = type;
        this.guildID = guildID;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
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

}

