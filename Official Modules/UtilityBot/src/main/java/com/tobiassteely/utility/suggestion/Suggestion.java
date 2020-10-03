package com.tobiassteely.utility.suggestion;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;

public class Suggestion extends ManagerObject {

    private String channelID;
    private String ownerID;
    private String suggestion;

    public Suggestion(String messageID, String ownerID, String channelID, String suggestion) {
        super(messageID);
        this.ownerID = ownerID;
        this.channelID = channelID;
        this.suggestion = suggestion;
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

}
