package com.tobiassteely.review;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.config.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

public class ReviewManager extends ManagerParent<Review> {

    private Config config;

    protected ReviewManager() {
        super("RVB.Review",true, "modules/review/data.json", new ReviewManagerHandler());
    }

    public void addReview(Review review) {
        addObject(review);
    }

    public Review getReview(String userID) {
        return getObject(userID);
    }

    public Review getReviewByMessage(String messageID) {
        return getCache("messageID").getObject(messageID);
    }

    public void removeReview(Review review) {
        removeObject(review.getKey());

        TextChannel textChannel = DiscordCore.getInstance().getBot().getTextChannelById(review.getChannelID());
        if(textChannel != null) {
            Message message = textChannel.retrieveMessageById(review.getMessageID()).complete();
            if(message != null) {
                message.delete().queue();
            }
        }
    }

}
