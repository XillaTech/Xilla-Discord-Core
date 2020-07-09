package net.xilla.discordcore.api.form;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public interface FormMessageEvent {

    boolean runEvent(Form form, GuildMessageReceivedEvent event);

}
