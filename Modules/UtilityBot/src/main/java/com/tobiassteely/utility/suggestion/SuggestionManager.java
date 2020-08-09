package com.tobiassteely.utility.suggestion;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.config.Config;
import org.json.simple.JSONObject;

public class SuggestionManager extends ManagerParent<Suggestion> {

    public SuggestionManager() {
        super(true);
    }

    @Override
    public JSONObject saveObject(Suggestion object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channelID", object.getChannelID());
        jsonObject.put("messageID", object.getMessageID());
        jsonObject.put("ownerID", object.getOwnerID());
        jsonObject.put("suggestion", object.getSuggestion());

        return jsonObject;
    }

    @Override
    public boolean loadObject(JSONObject json) {

        String messageID = json.get("messageID").toString();
        String channelID = json.get("channelID").toString();
        String ownerID = json.get("ownerID").toString();
        String suggestion = json.get("suggestion").toString();

        addObject(new Suggestion(messageID, ownerID, channelID, suggestion));

        return true;
    }

}
