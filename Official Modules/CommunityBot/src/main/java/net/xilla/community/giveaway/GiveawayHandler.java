package net.xilla.community.giveaway;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.community.CommunityBot;
import net.xilla.core.library.manager.Manager;

import javax.annotation.Nonnull;

public class GiveawayHandler extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if(!event.getUser().isBot()) {
            Manager<Giveaway> manager = CommunityBot.getInstance().getGiveawayManager().getManager(event.getGuild());
            Giveaway giveaway = manager.get(event.getMessageId());
            if(giveaway != null) {
                giveaway.getUsers().add(event.getUserId());
            }
            manager.save();
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event) {
        if(event.getUser() != null && event.getUser().isBot()) {
            Manager<Giveaway> manager = CommunityBot.getInstance().getGiveawayManager().getManager(event.getGuild());
            Giveaway giveaway = manager.get(event.getMessageId());
            if(giveaway != null) {
                giveaway.getUsers().remove(event.getUserId());
            }
            manager.save();
        }
    }

}
