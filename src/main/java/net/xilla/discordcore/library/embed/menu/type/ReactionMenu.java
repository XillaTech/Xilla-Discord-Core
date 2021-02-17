package net.xilla.discordcore.library.embed.menu.type;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.library.embed.menu.EmbedMenu;
import net.xilla.discordcore.library.embed.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ReactionMenu extends EmbedMenu {

    private EmbedBuilder embedBuilder;

    public ReactionMenu(Member member, List<MenuItem> items) {
        super(member, items);
        this.embedBuilder = getEmbed(getMember().getEffectiveName(), member.getGuild());
    }

    public ReactionMenu(EmbedBuilder embedBuilder, Member member, List<MenuItem> items) {
        super(member, items);
        this.embedBuilder = embedBuilder;
    }

    @Override
    public void send(TextChannel channel) {
        EmbedBuilder builder = embedBuilder;

        List<String> options = new ArrayList<>();
        for(MenuItem item : getItems()) {
            options.add(EmojiParser.parseToUnicode(item.getEmoji()) + " " + item.getName());
        }
        builder.addField("Options", String.join("\n", options), false);

        channel.sendMessage(builder.build()).queue((m) -> {
            setMessage(m);
            for(MenuItem item : getItems()) {
                m.addReaction(EmojiParser.parseToUnicode(item.getEmoji())).queue();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ignored) {}
            }
        });
    }

}
