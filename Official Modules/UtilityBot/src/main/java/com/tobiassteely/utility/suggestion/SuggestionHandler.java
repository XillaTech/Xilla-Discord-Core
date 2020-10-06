package com.tobiassteely.utility.suggestion;

import com.tobiassteely.utility.UtilityBot;
import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.DiscordCore;

import javax.annotation.Nonnull;
import java.awt.*;

public class SuggestionHandler extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(!event.getAuthor().isBot()) {
            if (event.getChannel().getId().equalsIgnoreCase(UtilityBot.getInstance().getSuggestionSettings().getChannelID())) {
                event.getMessage().delete().queue();

                EmbedBuilder embedBuilder = new EmbedBuilder();

                embedBuilder.setTitle("Suggestion:").setDescription(event.getMessage().getContentRaw());
                embedBuilder.setFooter(event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());
                embedBuilder.setColor(Color.decode(DiscordCore.getInstance().getSettings().getEmbedColor()));

                TextChannel channel = DiscordCore.getInstance().getBot().getTextChannelById(UtilityBot.getInstance().getSuggestionSettings().getChannelID());
                assert channel != null;
                Message message = channel.sendMessage(embedBuilder.build()).complete();

                message.addReaction(EmojiParser.parseToUnicode(":thumbsup:")).complete();
                message.addReaction(EmojiParser.parseToUnicode(":thumbsdown:")).complete();

                Suggestion suggestion = new Suggestion(message.getId(), event.getAuthor().getId(), channel.getId(), event.getMessage().getContentRaw());
                UtilityBot.getInstance().getSuggestionManager().put(suggestion);
                UtilityBot.getInstance().getSuggestionManager().save();
            }
        }
    }
}
