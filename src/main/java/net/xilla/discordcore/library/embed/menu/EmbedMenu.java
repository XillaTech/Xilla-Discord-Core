package net.xilla.discordcore.library.embed.menu;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.library.DiscordAPI;
import net.xilla.discordcore.library.program.ProgramInterface;

import java.util.List;

public abstract class EmbedMenu extends ListenerAdapter implements ProgramInterface {

    @Getter
    private Long startTime = System.currentTimeMillis();

    @Setter
    @Getter
    private Message message = null;

    @Getter
    private Member member;

    @Getter
    private List<MenuItem> items;

    @Getter
    private boolean waiting = true;

    public EmbedMenu(Member member, List<MenuItem> items) {
        this.member = member;
        this.items = items;
        DiscordAPI.getBot().addEventListener(this);
    }

    protected abstract void send(TextChannel channel);

    protected void end(Member member, MenuItem item) {
        waiting = false;
        item.getAction().run(member, item);
        DiscordAPI.getBot().removeEventListener(this);
    }

    public boolean isRunning() {
        return waiting;
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(!event.getUser().isBot()) {
            if(message != null && event.getMessageId().equals(message.getId())) {
                String reaction = EmojiParser.parseToAliases(event.getReactionEmote().getEmoji());
                for(MenuItem item : items) {
                    if(reaction.equals(item.getEmoji())) {
                        end(event.getMember(), item);
                    }
                }
            }
        }
    }

}
