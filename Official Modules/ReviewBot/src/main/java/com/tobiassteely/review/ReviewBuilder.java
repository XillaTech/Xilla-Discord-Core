package com.tobiassteely.review;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.library.form.MultiForm;
import net.xilla.discordcore.library.form.form.reaction.ReactionQuestion;
import net.xilla.discordcore.library.form.form.reaction.ReactionQuestionList;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;

public class ReviewBuilder extends CoreObject {

    public void build(User user, TextChannel channel) {
        MultiForm multiForm = new MultiForm("Review", channel.getId(), (results) -> {
            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Review");
            embedBuilder.setColor(Color.decode(getCoreSetting().getEmbedColor()));
            try {
                System.out.println(results.get("Rating").getEmoji());
                int rating = Integer.parseInt(results.get("Rating").getResponse());
                if(rating < 0 || rating > 5)
                    throw new Exception("Out of bounds");
                String reason = results.get("Description").getResponse();
                //String userID, int rating, String text, String date, String channelID, String messageID

                String reviewChannelID = ReviewBot.getInstance().getReviewSettings().getReviewChannel();
                TextChannel textChannel = DiscordCore.getInstance().getBot().getTextChannelById(reviewChannelID);
                if(textChannel != null) {
                    Review oldReview = ReviewBot.getInstance().getReviewManager().get(user.getId());
                    if(oldReview != null) {
                        ReviewBot.getInstance().getReviewManager().remove(oldReview);
                    }

                    String date = new Date(System.currentTimeMillis()).toString();

                    StringBuilder stars = new StringBuilder();
                    for(int i = 0; i < rating; i++) {
                        stars.append(EmojiParser.parseToUnicode(":star:"));
                    }

                    EmbedBuilder reviewEmbed = new EmbedBuilder().setTitle("Rating: " + rating + "/5 (" + stars.toString() + ")");
                    reviewEmbed.setColor(Color.decode(getCoreSetting().getEmbedColor()));
                    reviewEmbed.setDescription(reason);
                    reviewEmbed.setThumbnail(user.getAvatarUrl());
                    reviewEmbed.setAuthor(user.getName());
                    reviewEmbed.setFooter(date);

                    Message message = textChannel.sendMessage(reviewEmbed.build()).complete();

                    if(message != null) {
                        Review review = new Review(user.getId(), rating, reason, date, textChannel.getId(), message.getId());
                        ReviewBot.getInstance().getReviewManager().put(review);
                        ReviewBot.getInstance().getReviewManager().save();
                        embedBuilder.setDescription("Your review has been successfully made.");
                    } else {
                        embedBuilder.setDescription("There was an unknown error while reviewing, please contact the developer.");
                    }
                } else {
                    embedBuilder.setDescription("The review bot is not properly setup, please contact the server owner.");
                }
            } catch (Exception ex) {
                embedBuilder.setDescription("Invalid input, please try again!");
            }

            channel.sendMessage(embedBuilder.build()).queue();

        });
        ReactionQuestionList reactions = new ReactionQuestionList();
        reactions.addQuestion(new ReactionQuestion(":one:", "1"));
        reactions.addQuestion(new ReactionQuestion(":two:", "2"));
        reactions.addQuestion(new ReactionQuestion(":three:", "3"));
        reactions.addQuestion(new ReactionQuestion(":four:", "4"));
        reactions.addQuestion(new ReactionQuestion(":five:", "5"));
        multiForm.addReactionQuestion("Rating", "What score would you give them (1 lowest -> 5 highest)", reactions, user.getId(), channel.getGuild().getId());
        multiForm.addMessageQuestion("Description", "Why do they deserve this rating?", user.getId(), channel.getGuild().getId());
        multiForm.start();
    }

}
