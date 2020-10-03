package net.xilla.discordcore.core.command;

import net.xilla.discordcore.core.command.response.CommandResponse;

public interface CommandPermissionError {

    CommandResponse getResponse(String[] args, CommandData data);

}
