package net.xilla.discord.manager.command.console;

import net.xilla.core.library.manager.Manager;
import net.xilla.discord.api.command.Command;
import net.xilla.discord.api.console.ConsoleProcessor;
import net.xilla.discord.manager.command.discord.DiscordCommand;

import java.util.ArrayList;
import java.util.List;

public class ConsoleManager extends Manager<String, ConsoleCommand> implements ConsoleProcessor {

    public ConsoleManager() {
        super("ConsoleCommand");
    }

    @Override
    public void removeObject(Command object) {
        remove(object.getName());
    }

    @Override
    public List<Command> listObjects() {
        return new ArrayList<>(iterate());
    }

    @Override
    public void putObject(Command object) {
        put((ConsoleCommand) object);
    }

}
