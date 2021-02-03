package net.xilla.discordcore.library.form.form;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface FormMessageEvent {

    boolean runEvent(Form form, GuildMessageReceivedEvent event);

}
