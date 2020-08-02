package net.xilla.discordcore.command.cmd;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import com.tobiassteely.tobiasapi.command.response.CommandResponse;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.response.CoreCommandResponse;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HelpCommand extends CoreObject {

    public HelpCommand() {
        Command command = getCommandManager().getCommand("Help");

        ArrayList<CommandExecutor> executors = new ArrayList<>();
        executors.add((data) -> {
            HashMap<String, ArrayList<String>> commands = new HashMap<>();

            for(ManagerObject object : getCommandManager().getList()) {
                Command legacyCommand = (Command) object;

                if(data.getInputType().equalsIgnoreCase("commandline")) {
                    if(legacyCommand.isConsoleSupported()) {
                        if(!commands.containsKey(legacyCommand.getModule())) {
                            commands.put(legacyCommand.getModule(), new ArrayList<>());
                        }

                        if(legacyCommand.getPermission() == null || data.getUser().hasPermission(legacyCommand.getPermission())) {
                            commands.get(legacyCommand.getModule()).add(getCoreSetting().getCommandPrefix() + legacyCommand.getUsage() + " - " + legacyCommand.getDescription());
                        }
                    }
                } else {
                    if(!commands.containsKey(legacyCommand.getModule())) {
                        commands.put(legacyCommand.getModule(), new ArrayList<>());
                    }

                    if(legacyCommand.getPermission() == null || data.getUser().hasPermission(legacyCommand.getPermission())) {
                        commands.get(legacyCommand.getModule()).add(getCoreSetting().getCommandPrefix() + legacyCommand.getUsage() + " - " + legacyCommand.getDescription());
                    }
                }
            }

            StringBuilder description = new StringBuilder();

            int loop = 0;
            for(String module : commands.keySet()) {
                loop++;
                ArrayList<String> lines = commands.get(module);

                if(lines.size() > 0) {

                    description.append("**").append(module).append(" Commands**\n");
                    for (String line : lines) {
                        description.append(line).append("\n");
                    }

                    if (loop != commands.size()) {
                        description.append("\n");
                    }
                }
            }

            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Available Commands");
            embedBuilder.setDescription(description.toString());
            embedBuilder.setColor(Color.decode(getCoreSetting().getEmbedColor()));

            return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
        });

        command.setExecutors(executors);
    }

}
