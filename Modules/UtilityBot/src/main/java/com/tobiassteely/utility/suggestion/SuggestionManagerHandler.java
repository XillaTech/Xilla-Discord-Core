package com.tobiassteely.utility.suggestion;

import com.tobiassteely.tobiasapi.api.manager.ManagerEventExecutor;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import org.json.simple.JSONObject;

public class SuggestionManagerHandler implements ManagerEventExecutor<Suggestion> {
    @Override
    public Suggestion loadObject(JSONObject json) {
        String messageID = json.get("messageID").toString();
        String channelID = json.get("channelID").toString();
        String ownerID = json.get("ownerID").toString();
        String suggestion = json.get("suggestion").toString();

        return new Suggestion(messageID, ownerID, channelID, suggestion);
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
    public void onObjectAdd(ManagerParent<Suggestion> managerParent, Suggestion suggestion) {

    }

    @Override
    public void onObjectRemove(ManagerParent<Suggestion> managerParent, String s, Suggestion suggestion) {

    }

    @Override
    public void onReload(ManagerParent<Suggestion> managerParent) {

    }

    @Override
    public void onLoad(ManagerParent<Suggestion> managerParent) {

    }

    @Override
    public void onUnload(ManagerParent<Suggestion> managerParent) {

    }

    @Override
    public void onSave(ManagerParent<Suggestion> managerParent, JSONObject jsonObject) {

    }
}
