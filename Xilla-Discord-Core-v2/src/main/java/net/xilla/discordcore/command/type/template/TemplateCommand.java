package net.xilla.discordcore.command.type.template;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.CommandResponse;
import net.xilla.discordcore.command.type.legacy.LegacyCommand;
import org.json.simple.JSONObject;

public class TemplateCommand extends LegacyCommand {

    public TemplateCommand(String name, String[] activators, String description, String usage, int staffLevel) {
        super(name, activators, "Core", description, usage, staffLevel);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        CommandResponse commandResponse = runTemplate(args, event);
        if(commandResponse != null) {
            if(event != null) {
                commandResponse.send(event.getTextChannel());
            } else {
                commandResponse.send();
            }
        }

        return true;
    }

    public CommandResponse runTemplate(String[] args, MessageReceivedEvent event) {
        return null; // OVERRIDE
    }

    public JSONObject getJSON() {
        return null; // OVERRIDE
    }

}
