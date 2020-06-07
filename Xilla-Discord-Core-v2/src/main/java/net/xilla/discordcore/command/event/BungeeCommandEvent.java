package net.xilla.discordcore.command.event;

import net.md_5.bungee.api.CommandSender;

public class BungeeCommandEvent {

    private CommandSender sender;

    public BungeeCommandEvent(CommandSender sender) {
        this.sender = sender;
    }

    public CommandSender getSender() {
        return sender;
    }
}
