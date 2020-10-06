package com.tobiassteely.review;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

public class ReviewManager extends Manager<Review> {

    protected ReviewManager() {
        super("RVB.Review", "modules/review/data.json");
    }


    public Review getReviewByMessage(String messageID) {
        return getCache("messageID").getObject(messageID);
    }

    @Override
    protected void load() {
        for(Object obj : getData().values()) {
            put(new Review((JSONObject)obj));
        }
    }

    @Override
    protected void objectAdded(Review review) {
        getCache("messageID").putObject(review.getMessageID(), review);
    }

    @Override
    protected void objectRemoved(Review review) {
        TextChannel textChannel = DiscordCore.getInstance().getBot().getTextChannelById(review.getChannelID());
        if(textChannel != null) {
            Message message = textChannel.retrieveMessageById(review.getMessageID()).complete();
            if(message != null) {
                message.delete().queue();
            }
        }
        getCache("messageID").removeObject(review.getMessageID());
    }
}
