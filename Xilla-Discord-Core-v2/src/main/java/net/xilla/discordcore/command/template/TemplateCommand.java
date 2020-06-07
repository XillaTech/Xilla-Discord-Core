package net.xilla.discordcore.command.template;

import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.CoreCommand;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.event.BungeeCommandEvent;
import net.xilla.discordcore.command.event.SpigotCommandEvent;
import org.json.simple.JSONObject;

public class TemplateCommand extends CoreCommand {

    public TemplateCommand(String name, String[] activators, String description, String usage, int staffLevel, CommandExecutor executor) {
        super("Core", name, activators, usage, description, staffLevel, executor);
    }

    @Override
    public void run(String[] args, String inputType, Object... data) {
        runTemplate(args, inputType, data);
    }

    public void runTemplate(String[] args, String inputType, Object... data) {
        CommandExecutor commandExecutor = getExecutor();
        if(commandExecutor instanceof CoreCommandExecutor) {
            CoreCommandExecutor executor = (CoreCommandExecutor)commandExecutor;

            executor.run(getName(), args, inputType, data);
        }

        commandExecutor.run(getName(), args, inputType, data);
    }

    public JSONObject getJSON() {
        return null; // Override this in the child-class
    }

}
