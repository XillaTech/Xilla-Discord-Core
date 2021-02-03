package net.xilla.discordcore.core.command.action;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class EditAction implements PostCommandAction {

    private String msg = null;

    private EmbedBuilder embedBuilder = null;

    public EditAction(String msg) {
        this.msg = msg;
    }

    public EditAction(EmbedBuilder embedBuilder) {
        this.embedBuilder = embedBuilder;
    }

    @Override
    public void run(Message message) {
        if(msg == null) {
            message.editMessage(embedBuilder.build()).queue();
        } else {
            message.editMessage(message).queue();
        }
    }

}
