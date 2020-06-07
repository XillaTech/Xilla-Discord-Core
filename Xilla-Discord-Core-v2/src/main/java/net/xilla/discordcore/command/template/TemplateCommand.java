package net.xilla.discordcore.command.template;

import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import org.json.simple.JSONObject;

public class TemplateCommand extends Command {

    public TemplateCommand(String name, String[] activators, String description, String usage, int staffLevel, CommandExecutor executor) {
        super("Core", name, activators, usage, description, staffLevel, executor);
    }

    @Override
    public void run(String rawCommand, String[] args, String inputType) {
        runTemplate(rawCommand, args, inputType);
    }

    public void runTemplate(String rawCommand, String[] args, String inputType) {
        getExecutor().run(getName(), args, inputType);
    }

    public JSONObject getJSON() {
        return null; // Override this in the child-class
    }

}
