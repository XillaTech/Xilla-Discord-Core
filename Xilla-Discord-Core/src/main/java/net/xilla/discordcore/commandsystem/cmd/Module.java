package net.xilla.discordcore.commandsystem.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.commandsystem.CommandObject;

public class Module extends CommandObject {

    public Module() {
        super("Module", new String[] {"module"}, "Core", "Manage the modules", "module", 10);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        if(event == null) {

        } else {

        }

        return true;
    }
}
