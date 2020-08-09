package com.tobiassteely.review;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.response.CoreCommandResponse;

import java.awt.*;

public class ReviewCommands extends CoreObject {

    public ReviewCommands() {
        reviewCommand();
        reviewAdmin();
    }

    public void reviewCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Review", "Review", false);
        commandBuilder.setUsage("review");
        commandBuilder.setActivators("review");
        commandBuilder.setDescription("review");
        commandBuilder.setDescription("Leave a review!");
        commandBuilder.setCommandExecutor((data) -> {
            if(data.get() instanceof MessageReceivedEvent) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();

                new ReviewBuilder().build(event.getAuthor(), event.getTextChannel());

                return null;
            } else {
                return new CoreCommandResponse(data).setDescription("This command is for discord only");
            }
        });
        commandBuilder.build();
    }

    public void reviewAdmin() {
        CommandBuilder commandBuilder = new CommandBuilder("Review", "ReviewAdmin", true);
        commandBuilder.setModule("Review");
        commandBuilder.setUsage("reviewadmin");
        commandBuilder.setActivators("reviewadmin", "ra");
        commandBuilder.setName("ReviewAdmin");
        commandBuilder.setDescription("Manage the reviews.");
        commandBuilder.setPermission("core.review");
        commandBuilder.setCommandExecutor((data) -> {
            if(data.get() instanceof MessageReceivedEvent) {

                EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Review Admin");
                embedBuilder.setColor(Color.decode(getCoreSetting().getEmbedColor()));

                if(data.getArgs().length == 2 && data.getArgs()[0].equalsIgnoreCase("delete")) {
                    String id = data.getArgs()[1].replace("<@!", "").replace(">","");
                    Review review = ReviewBot.getInstance().getReviewManager().getReviewByMessage(id);
                    if(review == null) {
                        review = ReviewBot.getInstance().getReviewManager().getReview(id);
                    }

                    if (review != null) {

                        ReviewBot.getInstance().getReviewManager().removeReview(review);
                        ReviewBot.getInstance().getReviewManager().save();
                        embedBuilder.setDescription("That review has been removed!");
                    } else {
                        embedBuilder.setDescription("That is not a valid message or user!");
                    }
                } else {
                    String prefix = getCoreSetting().getCommandPrefix();
                    embedBuilder.setDescription(prefix + "reviewadmin delete <message id>\n" + prefix + "reviewadmin delete <user id>");
                }

                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This command is for discord only");
            }
        });
        commandBuilder.build();
    }

}
