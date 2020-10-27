package net.xilla.community.punishment;

import lombok.Getter;
import lombok.Setter;
import net.xilla.core.library.json.SerializedObject;
import net.xilla.core.library.json.XillaJson;

public class Punishment implements SerializedObject {

    @Getter
    @Setter
    private String userID;

    @Getter
    @Setter
    private String staffID;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private long start;

    @Getter
    @Setter
    private long duration;

    @Getter
    @Setter
    private boolean active;

    @Getter
    @Setter
    private String description;

    public Punishment(String userID, String staffID, String type, long start, long duration, boolean active, String description) {
        this.userID = userID;
        this.staffID = staffID;
        this.type = type;
        this.start = start;
        this.duration = duration;
        this.active = active;
        this.description = description;
    }

    public Punishment() {

    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("userID", userID);
        json.put("staffID", staffID);
        json.put("type", type);
        json.put("start", start);
        json.put("duration", duration);
        json.put("active", active);
        json.put("description", description);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        this.userID = xillaJson.get("userID");
        this.staffID = xillaJson.get("staffID");
        this.type = xillaJson.get("type");
        this.start = Long.parseLong(xillaJson.get("start").toString());
        this.duration = Long.parseLong(xillaJson.get("duration").toString());
        this.active = xillaJson.get("active");
        this.description = xillaJson.get("description");
    }
}
