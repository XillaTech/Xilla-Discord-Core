package net.xilla.discordcore.library.embed.menu.type;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.library.QueueHandler;
import net.xilla.discordcore.library.embed.menu.EmbedMenu;
import net.xilla.discordcore.library.embed.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class PaginationMenu extends EmbedMenu {

    private EmbedBuilder embedBuilder;

    private int page = 1;

    private int perPage = 1;

    private int totalPages;

    public PaginationMenu(Member member, List<MenuItem> items, int perPage) {
        super(member, items);

        this.embedBuilder = getEmbed(getMember().getEffectiveName(), member.getGuild());
        this.perPage = perPage;
        this.totalPages = (items.size() / perPage) + 1;
    }

    public PaginationMenu(EmbedBuilder embedBuilder, Member member, List<MenuItem> items, int perPage) {
        super(member, items);

        this.embedBuilder = embedBuilder;
        this.perPage = perPage;
        this.totalPages = (items.size() / perPage) + 1;
    }

    @Override
    public void send(TextChannel channel) {
        send(channel, 1);
    }

    public void send(TextChannel channel, int page) {

        EmbedBuilder builder = new EmbedBuilder(embedBuilder);

        int start = (page - 1) * perPage;
        int end = page * perPage;

        List<String> options = new ArrayList<>();
        for (int i = start; i < end && i < getItems().size(); i++) {
            MenuItem item = getItems().get(i);

            options.add(EmojiParser.parseToUnicode(item.getEmoji()) + " " + item.getName());
        }

        builder.addField("Options (Page " + page + "/" + totalPages + ")", String.join("\n", options), false);

        if (getMessage() != null) {
            getMessage().clearReactions().queue();
            getMessage().editMessage(builder.build()).queue((m) -> {
                setMessage(m);

                QueueHandler queueHandler = new QueueHandler();

                if (page > 1) {
                    String emoji = EmojiParser.parseToUnicode(":arrow_left:");
                    queueHandler.addRestAction(m.addReaction(emoji));
                }


                for (int i = start; i < end && i < getItems().size(); i++) {
                    MenuItem item = getItems().get(i);

                    String emoji = EmojiParser.parseToUnicode(item.getEmoji());

                    queueHandler.addRestAction(m.addReaction(emoji));
                }

                /*
                 * By moving this if statement below the for loop, the right arrow will always be at the end of the
                 * reaction list. This is more logical then the previous implementation
                 */

                if (page < totalPages) {
                    String emoji = EmojiParser.parseToUnicode(":arrow_right:");
                    queueHandler.addRestAction(m.addReaction(emoji));
                }
                queueHandler.start();
            });

        } else {
            channel.sendMessage(builder.build()).queue((m) -> {
                setMessage(m);

                QueueHandler queueHandler = new QueueHandler();

                if (page > 1) {
                    String emoji = EmojiParser.parseToUnicode(":arrow_left:");
                    queueHandler.addRestAction(m.addReaction(emoji));
                }


                for (int i = start; i < end && i < getItems().size(); i++) {
                    MenuItem item = getItems().get(i);

                    String emoji = EmojiParser.parseToUnicode(item.getEmoji());

                    queueHandler.addRestAction(m.addReaction(emoji));
                }

                /*
                 * By moving this if statement below the for loop, the right arrow will always be at the end of the
                 * reaction list. This is more logical then the previous implementation
                 */

                if (page < totalPages) {
                    String emoji = EmojiParser.parseToUnicode(":arrow_right:");
                    queueHandler.addRestAction(m.addReaction(emoji));
                }
                queueHandler.start();
            });
        }
    }

    public void sendNext(TextChannel channel) {
        page++;
        send(channel, page);
    }

    public void sendFirst(TextChannel channel) {
        page = 1;
        send(channel, page);
    }

    public void sendLast(TextChannel channel) {
        page = totalPages;
        send(channel, page);
    }

    public void sendPrevious(TextChannel channel) {
        page--;
        send(channel, page);
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (!event.getUser().isBot()) {
            if (getMessage() != null && event.getMessageId().equals(getMessage().getId())) {
                String reaction = EmojiParser.parseToAliases(event.getReactionEmote().getEmoji());

                if (reaction.equalsIgnoreCase(":arrow_right:")) {
                    sendNext(event.getChannel());
                } else if (reaction.equalsIgnoreCase(":arrow_left:")) {
                    sendPrevious(event.getChannel());
                }

                for (MenuItem item : getItems()) {
                    if (reaction.equals(item.getEmoji())) {
                        end(event.getMember(), item);
                    }
                }
            }
        }
    }

}
