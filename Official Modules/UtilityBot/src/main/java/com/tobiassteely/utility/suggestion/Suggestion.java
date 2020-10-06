package com.tobiassteely.utility.suggestion;

import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import org.json.simple.JSONObject;

public class Suggestion extends ManagerObject {

    private String channelID;
    private String ownerID;
    private String suggestion;

    public Suggestion(String messageID, String ownerID, String channelID, String suggestion) {
        super(messageID, "UTB.Suggestion");
        this.ownerID = ownerID;
        this.channelID = channelID;
        this.suggestion = suggestion;
    }

    public Suggestion(JSONObject json) {
        super(json.get("messageID").toString(), "UTB.Suggestion");

        this.channelID = json.get("channelID").toString();
        this.ownerID = json.get("ownerID").toString();
        this.suggestion = json.get("suggestion").toString();
    }

    public String getChannelID() {
        return channelID;
    }

    public String getMessageID() {
        return getKey();
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getSuggestion() {
        return suggestion;
    }

    @Override
    public XillaJson getSerializedData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channelID", getChannelID());
        jsonObject.put("messageID", getMessageID());
        jsonObject.put("ownerID", getOwnerID());
        jsonObject.put("suggestion", getSuggestion());

        return new XillaJson(jsonObject);
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }
}
