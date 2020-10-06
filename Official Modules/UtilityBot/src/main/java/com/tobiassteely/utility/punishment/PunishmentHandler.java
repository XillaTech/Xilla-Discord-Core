package com.tobiassteely.utility.punishment;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;

public class PunishmentHandler extends ListenerAdapter {

    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        List<Punishment> punishments = PunishmentManager.mutes.get(event.getAuthor().getId());

    }

}
