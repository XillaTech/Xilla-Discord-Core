package net.xilla.discordcore.core.command.action;

import net.dv8tion.jda.api.entities.Message;

public class DeleteAction implements PostCommandAction {

    @Override
    public void run(Message message) {
        message.delete().queue();
    }

}
