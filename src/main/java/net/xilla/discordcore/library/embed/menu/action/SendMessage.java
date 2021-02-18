package net.xilla.discordcore.library.embed.menu.action;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.library.embed.menu.MenuAction;
import net.xilla.discordcore.library.embed.menu.MenuItem;

public class SendMessage implements MenuAction {

    private String name;

    private TextChannel channel;

    public SendMessage(TextChannel channel, String name) {
        this.channel = channel;
        this.name = name;
    }

    @Override
    public void run(Member member, MenuItem item) {
        this.channel.sendMessage(name).queue();
    }

}
