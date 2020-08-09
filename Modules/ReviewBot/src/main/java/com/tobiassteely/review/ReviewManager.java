package com.tobiassteely.review;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.config.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

public class ReviewManager extends ManagerParent {

    private Config config;

    protected ReviewManager() {
        super(false);
        this.config = getConfigManager().getConfig("modules/review/data.json");
        addCache("messageID");
        reload();
    }

    public void addReview(Review review) {
        addObject(review);
        getCache("messageID").putObject(review.getMessageID(), review);
    }

    public Review getReview(String userID) {
        return (Review)getObject(userID);
    }

    public Review getReviewByMessage(String messageID) {
        return (Review)getCache("messageID").getObject(messageID);
    }

    public void removeReview(Review review) {
        getCache("messageID").removeObject(review.getMessageID());
        removeObject(review.getKey());

        TextChannel textChannel = DiscordCore.getInstance().getBot().getTextChannelById(review.getChannelID());
        if(textChannel != null) {
            Message message = textChannel.retrieveMessageById(review.getMessageID()).complete();
            if(message != null) {
                message.delete().queue();
            }
        }
    }

    @Override
    public void reload() {
        JSONObject json = config.getJSON("reviews");
        if(json != null) {
            for(Object object : json.keySet()) {
                JSONObject jsonObject = (JSONObject) json.get(object);

                String userID = jsonObject.get("userID").toString();
                String date = jsonObject.get("date").toString();
                String text = jsonObject.get("text").toString();
                String channelID = jsonObject.get("channelID").toString();
                String messageID = jsonObject.get("messageID").toString();
                int rating = Integer.parseInt(jsonObject.get("rating").toString());

                //String userID, int rating, String text, String date
                Review review = new Review(userID, rating, text, date, channelID, messageID);
                addReview(review);
            }
        }
    }

    public void save() {
        JSONObject json = new JSONObject();
        for(Object object : getList()) {
            JSONObject reviewJson = new JSONObject();
            Review review = (Review)object;
            reviewJson.put("userID", review.getUserID());
            reviewJson.put("date", review.getDate());
            reviewJson.put("rating", review.getRating());
            reviewJson.put("text", review.getText());
            reviewJson.put("channelID", review.getChannelID());
            reviewJson.put("messageID", review.getMessageID());

            json.put(review.getKey(), reviewJson);
        }

        config.set("reviews", json);
        config.save();
    }

}
