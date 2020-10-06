package com.tobiassteely.review;

import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import org.json.simple.JSONObject;

public class Review extends ManagerObject {

    private int rating;
    private String text;
    private String date;
    private String channelID;
    private String messageID;

    public Review(String userID, int rating, String text, String date, String channelID, String messageID) {
        super(userID, "RVB.Review");
        this.rating = rating;
        this.text = text;
        this.date = date;
        this.channelID = channelID;
        this.messageID = messageID;
    }

    public Review(JSONObject jsonObject) {
        super(jsonObject.get("userID").toString(), "RVB.Review");
        this.date = jsonObject.get("date").toString();
        this.text = jsonObject.get("text").toString();
        this.channelID = jsonObject.get("channelID").toString();
        this.messageID = jsonObject.get("messageID").toString();
        this.rating = Integer.parseInt(jsonObject.get("rating").toString());
    }

    public int getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getUserID() {
        return getKey();
    }

    public String getChannelID() {
        return channelID;
    }

    public String getMessageID() {
        return messageID;
    }

    @Override
    public XillaJson getSerializedData() {
        JSONObject reviewJson = new JSONObject();
        reviewJson.put("userID", getUserID());
        reviewJson.put("date", getDate());
        reviewJson.put("rating", getRating());
        reviewJson.put("text", getText());
        reviewJson.put("channelID", getChannelID());
        reviewJson.put("messageID", getMessageID());

        return new XillaJson(reviewJson);
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }
}
