package net.xilla.discordcore.command.template;

import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandData;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import com.tobiassteely.tobiasapi.command.response.CommandResponse;
import net.xilla.discordcore.command.CoreCommandExecutor;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class TemplateCommand extends Command {

    public TemplateCommand(String module, String name, String[] activators, String description, String usage, String permission, CommandExecutor executor) {
        super(module, name, activators, usage, description, permission, Collections.singletonList(executor));
    }

    @Override
    public ArrayList<CommandResponse> run(CommandData data) {
        return runTemplate(data);
    }

    public ArrayList<CommandResponse> runTemplate(CommandData data) {
        ArrayList<CommandResponse> responses = new ArrayList<>();
        for(CommandExecutor commandExecutor : getExecutors()) {
            CommandExecutor executor = commandExecutor;

            CommandResponse response = executor.run(data);
            if(response != null) {
                responses.add(response);
            }
        }

        return responses;
    }

    public JSONObject getJSON() {
        return null; // Override this in the child-class
    }

}
