package com.tobiassteely.utility.suggestion;

import com.tobiassteely.utility.UtilityBot;
import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.form.MultiForm;

import java.awt.*;

public class SuggestionCommands extends CoreObject {

    public SuggestionCommands() {
        suggestionCommand();
        suggestionAdmin();
    }

    public void suggestionCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Suggestions", "Suggest");
        commandBuilder.setActivators("suggest", "suggestions");
        commandBuilder.setDescription("Suggest a change/update!");
        commandBuilder.setCommandExecutor((data) -> {
            if(data.get() instanceof MessageReceivedEvent) {
                MessageReceivedEvent event = (MessageReceivedEvent)data.get();

                MultiForm multiForm = new MultiForm("Suggestion", event.getTextChannel().getId(), (results) -> {
                    EmbedBuilder embedBuilder = new EmbedBuilder();

                    embedBuilder.setTitle("Suggestion:").setDescription(results.get("Suggestion").getResponse());
                    embedBuilder.setFooter(event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());
                    embedBuilder.setColor(Color.decode(getDiscordCore().getSettings().getEmbedColor()));

                    TextChannel channel = getBot().getTextChannelById(UtilityBot.getInstance().getSuggestionSettings().getChannelID());
                    assert channel != null;
                    Message message = channel.sendMessage(embedBuilder.build()).complete();

                    message.addReaction(EmojiParser.parseToUnicode(":thumbsup:")).complete();
                    message.addReaction(EmojiParser.parseToUnicode(":thumbsdown:")).complete();

                    Suggestion suggestion = new Suggestion(message.getId(), event.getAuthor().getId(), channel.getId(), results.get("Suggestion").getResponse());
                    UtilityBot.getInstance().getSuggestionManager().addObject(suggestion);
                    UtilityBot.getInstance().getSuggestionManager().save();
                });
                multiForm.addMessageQuestion("Suggestion", "What is your suggested update/change?", event.getTextChannel(), event.getAuthor().getId());
                multiForm.start();



                return null;
            } else {
                return new CoreCommandResponse(data).setDescription("This command is for discord only!");
            }
        });
        commandBuilder.build();
    }

    public void suggestionAdmin() {

    }

}
