package net.xilla.discordcore.library.embed.menu.action;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.library.embed.menu.MenuAction;
import net.xilla.discordcore.library.embed.menu.MenuItem;

public class SendEmbed implements MenuAction {

    private EmbedBuilder embedBuilder;

    private TextChannel channel;

    public SendEmbed(TextChannel channel, EmbedBuilder embedBuilder) {
        this.channel = channel;
        this.embedBuilder = embedBuilder;
    }

    @Override
    public void run(Member member, MenuItem item) {
        this.channel.sendMessage(embedBuilder.build()).queue();
    }

}
