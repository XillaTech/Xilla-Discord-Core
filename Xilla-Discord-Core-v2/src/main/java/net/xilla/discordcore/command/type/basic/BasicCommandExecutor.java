package net.xilla.discordcore.command.type.basic;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.CommandResponse;

public interface BasicCommandExecutor {

    CommandResponse run(String name, String[] args, MessageReceivedEvent event);

}
