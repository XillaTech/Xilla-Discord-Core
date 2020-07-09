package net.xilla.discordcore.api.form;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public interface FormReactionEvent {

    boolean runEvent(Form form, GuildMessageReactionAddEvent event);

}
