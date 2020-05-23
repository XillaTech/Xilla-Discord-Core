package net.xilla.discordcore.platform.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.type.full.FullCommand;

public class HelpCommand extends FullCommand {

    public HelpCommand() {
        super("Help", new String[] {"help", "?"}, "Core", "Sends this message", "help", 0, true);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        System.out.println("Reee");
        return true;
    }
}
