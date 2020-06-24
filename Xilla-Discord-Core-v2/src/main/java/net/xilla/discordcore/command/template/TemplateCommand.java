package net.xilla.discordcore.command.template;

import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import com.tobiassteely.tobiasapi.command.data.CommandData;
import com.tobiassteely.tobiasapi.command.response.CommandResponse;
import net.xilla.discordcore.command.CoreCommandExecutor;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static org.bukkit.Bukkit.getName;
import static org.bukkit.Bukkit.recipeIterator;

public class TemplateCommand extends Command {

    public TemplateCommand(String name, String[] activators, String description, String usage, CommandExecutor executor) {
        super("Core", name, activators, usage, description, Collections.singletonList(executor));
    }

    @Override
    public ArrayList<CommandResponse> run(String[] args, CommandData data) {
        return runTemplate(args, data);
    }

    public ArrayList<CommandResponse> runTemplate(String[] args, CommandData data) {
        ArrayList<CommandResponse> responses = new ArrayList<>();
        for(CommandExecutor commandExecutor : getExecutors()) {
            if(commandExecutor instanceof CoreCommandExecutor) {
                CoreCommandExecutor executor = (CoreCommandExecutor)commandExecutor;

                CommandResponse response = executor.run(getName(), args, data);
                if(response != null) {
                    responses.add(response);
                }
            }
        }

        return responses;
    }

    public JSONObject getJSON() {
        return null; // Override this in the child-class
    }

}
