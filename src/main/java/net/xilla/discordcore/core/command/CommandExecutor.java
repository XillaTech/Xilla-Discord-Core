package net.xilla.discordcore.core.command;

import net.xilla.discordcore.core.command.response.CommandResponse;

public interface CommandExecutor {

    CommandResponse run(CommandData data) throws Exception;

}
