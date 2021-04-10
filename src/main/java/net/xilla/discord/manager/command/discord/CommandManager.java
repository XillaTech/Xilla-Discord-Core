package net.xilla.discord.manager.command.discord;

import net.xilla.core.library.manager.Manager;
import net.xilla.discord.api.command.Command;
import net.xilla.discord.api.command.CommandProcessor;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends Manager<String, DiscordCommand> implements CommandProcessor {

    public CommandManager() {
        super("DiscordCommand");
    }

    @Override
    public void putObject(Command object) {
        put((DiscordCommand) object);
    }

    @Override
    public void removeObject(Command object) {
        remove(object.getName());
    }

    @Override
    public List<Command> listObjects() {
        return new ArrayList<>(iterate());
    }

}
