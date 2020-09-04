package net.xilla.discordcore.command.template;

import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandData;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import com.tobiassteely.tobiasapi.command.response.CommandResponse;
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

            if(getPermission() == null || data.getUser().hasPermission(getPermission())) {
                try {
                    CommandResponse response = executor.run(data);
                    if (response != null) {
                        responses.add(response);
                    }
                } catch (Exception ex) {
                    getLog().sendMessage(2, "Error while running command: " + data.getCommand());
                    ex.printStackTrace();
                    responses.add(new CommandResponse(data));
                }
            } else {
                responses.add(getCommandManager().getPermissionError().getResponse(data.getArgs(), data));
            }
        }

        return responses;
    }

    public JSONObject getJSON() {
        return null; // Override this in the child-class
    }

}
