package com.tobiassteely.review;

import com.tobiassteely.tobiasapi.api.manager.ManagerEventExecutor;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import org.json.simple.JSONObject;

public class ReviewManagerHandler implements ManagerEventExecutor<Review> {
    @Override
    public Review loadObject(JSONObject jsonObject) {
        String userID = jsonObject.get("userID").toString();
        String date = jsonObject.get("date").toString();
        String text = jsonObject.get("text").toString();
        String channelID = jsonObject.get("channelID").toString();
        String messageID = jsonObject.get("messageID").toString();
        int rating = Integer.parseInt(jsonObject.get("rating").toString());

        //String userID, int rating, String text, String date
        Review review = new Review(userID, rating, text, date, channelID, messageID);
        return review;
    }

    @Override
    public JSONObject saveObject(Review review) {
        JSONObject reviewJson = new JSONObject();
        reviewJson.put("userID", review.getUserID());
        reviewJson.put("date", review.getDate());
        reviewJson.put("rating", review.getRating());
        reviewJson.put("text", review.getText());
        reviewJson.put("channelID", review.getChannelID());
        reviewJson.put("messageID", review.getMessageID());

        return reviewJson;
    }

    @Override
    public void onObjectAdd(ManagerParent<Review> managerParent, Review review) {
        managerParent.getCache("messageID").putObject(review.getMessageID(), review);
    }

    @Override
    public void onObjectRemove(ManagerParent<Review> managerParent, String s, Review review) {
        managerParent.getCache("messageID").removeObject(review.getMessageID());
    }

    @Override
    public void onReload(ManagerParent<Review> managerParent) {

    }

    @Override
    public void onLoad(ManagerParent<Review> managerParent) {

    }

    @Override
    public void onUnload(ManagerParent<Review> managerParent) {

    }

    @Override
    public void onSave(ManagerParent<Review> managerParent, JSONObject jsonObject) {

    }
}
