package com.tobiassteely.review;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;

public class Review extends ManagerObject {

    private int rating;
    private String text;
    private String date;
    private String channelID;
    private String messageID;

    public Review(String userID, int rating, String text, String date, String channelID, String messageID) {
        super(userID);
        this.rating = rating;
        this.text = text;
        this.date = date;
        this.channelID = channelID;
        this.messageID = messageID;
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
}
