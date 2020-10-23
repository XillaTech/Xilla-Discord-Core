package net.xilla.discordcore.core.command.event;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SpigotCommandEvent {

    private CommandSender sender;
    private Command command;

    public SpigotCommandEvent(CommandSender sender, Command command) {
        this.sender = sender;
        this.command = command;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Command getCommand() {
        return command;
    }
}
