package net.xilla.discordcore.command.template;

import net.xilla.core.library.json.XillaJson;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.command.Command;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.core.command.CommandExecutor;
import net.xilla.discordcore.core.command.response.CommandResponse;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class TemplateCommand extends Command {

    public TemplateCommand(String module, String name, String[] activators, String description, String usage, Object permission, CommandExecutor executor) {
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
                    Logger.log(LogLevel.ERROR, "Error while running command: " + data.getCommand(), getClass());
                    Logger.log(ex, getClass());
                    responses.add(new CommandResponse(data));
                }
            } else {
                responses.add(DiscordCore.getInstance().getCommandManager().getPermissionError().getResponse(data.getArgs(), data));
            }
        }

        return responses;
    }

}
