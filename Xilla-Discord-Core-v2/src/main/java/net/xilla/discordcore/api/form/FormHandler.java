package net.xilla.discordcore.api.form;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.xilla.discordcore.api.DiscordAPI;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class FormHandler extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if(!event.getUser().isBot()) {
            Form form = DiscordAPI.getInstance().getFormManager().getForm(event.getMessageId());
            if (form != null) {
                if (form.getFormOwner().equalsIgnoreCase(event.getUserId())) {
                    if (form.getFormOptions() != null)
                        DiscordAPI.getInstance().getFormManager().runEvent(event);
                }
            }
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(!event.getAuthor().isBot() && DiscordAPI.getInstance().getFormManager().getFormsByUserID().containsKey(event.getAuthor().getId())) {
            ArrayList<Form> forms = DiscordAPI.getInstance().getFormManager().getFormsByUserID().get(event.getAuthor().getId());
            for (Form form : forms) {
                if (form.getFormOwner().equalsIgnoreCase(event.getAuthor().getId())) {
                    if (form.getFormOptions() == null)
                        DiscordAPI.getInstance().getFormManager().runEvent(event, form);
                }
            }
        }
    }

}
