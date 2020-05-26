package net.xilla.discordcore.platform.cmd;

import com.tobiassteely.tobiasapi.api.Log;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.CommandResponse;
import net.xilla.discordcore.command.type.legacy.LegacyCommand;

public class HelpCommand extends LegacyCommand {

    public HelpCommand() {
        super("Help", new String[] {"help", "?"}, "Core", "Sends this message", "help", 0);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        Log.sendMessage(0, "0 - Help");
        if(event == null) {
            new CommandResponse("Help").send();
        } else {
            new CommandResponse("Help").send(event.getTextChannel());
        }
        return true;
    }
}
