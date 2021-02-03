package net.xilla.discordcore.library.form.form;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.library.DiscordAPI;

import java.util.ArrayList;

public class FormHandler extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(!event.getUser().isBot()) {
            Form form = DiscordAPI.getFormManager().getForm(event.getMessageId());
            if (form != null && form.getChannelID().equalsIgnoreCase(event.getChannel().getId())) {
                if (form.getFormOwner().equalsIgnoreCase(event.getUserId())) {
                    if (form.getFormOptions() != null)
                        DiscordAPI.getFormManager().runEvent(event);
                }
            }
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!event.getAuthor().isBot() && DiscordAPI.getFormManager().getFormsByUserID().containsKey(event.getAuthor().getId())) {
            ArrayList<Form> forms = DiscordAPI.getFormManager().getFormsByUserID().get(event.getAuthor().getId());
            for (Form form : new ArrayList<>(forms)) {
                if(form.getChannelID().equalsIgnoreCase(event.getChannel().getId())) {
                    if (form.getFormOwner().equalsIgnoreCase(event.getAuthor().getId())) {
                        if (form.getFormOptions() == null)
                            DiscordAPI.getFormManager().runEvent(event, form);
                    }
                }
            }
        }
    }

}
