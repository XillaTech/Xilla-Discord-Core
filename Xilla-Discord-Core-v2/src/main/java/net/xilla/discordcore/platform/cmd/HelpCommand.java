package net.xilla.discordcore.platform.cmd;

import com.tobiassteely.tobiasapi.api.Log;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandResponse;
import net.xilla.discordcore.command.type.basic.BasicCommand;
import net.xilla.discordcore.command.type.legacy.LegacyCommand;
import net.xilla.discordcore.platform.CoreSettings;

import java.util.ArrayList;
import java.util.HashMap;

public class HelpCommand extends LegacyCommand {

    public HelpCommand() {
        super("Help", new String[] {"help", "?"}, "Core", "Sends this message", "help", 0);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        CoreSettings settings = DiscordCore.getInstance().getSettings();

        HashMap<String, ArrayList<String>> commands = new HashMap<>();

        for(BasicCommand command : DiscordCore.getInstance().getCommandManager().getBasicCommandsByName().values()) {
            if(event == null || DiscordCore.getInstance().getStaffManager().hasPermission(event.getGuild(), event.getAuthor(), command.getStaffLevel())) {

                if(!commands.containsKey(command.getModule())) {
                    commands.put(command.getModule(), new ArrayList<>());
                }

                commands.get(command.getModule()).add(settings.getCommandPrefix() + command.getName() + " - " + command.getDescription());
            }
        }
        for(ManagerObject object : DiscordCore.getInstance().getCommandManager().getList()) {
            LegacyCommand legacyCommand = (LegacyCommand) object;
            if(event == null || DiscordCore.getInstance().getStaffManager().hasPermission(event.getGuild(), event.getAuthor(), legacyCommand.getStaffLevel())) {

                if(!commands.containsKey(legacyCommand.getModule())) {
                    commands.put(legacyCommand.getModule(), new ArrayList<>());
                }

                commands.get(legacyCommand.getModule()).add(settings.getCommandPrefix() + legacyCommand.getUsage() + " - " + legacyCommand.getDescription());
            }
        }

        StringBuilder description = new StringBuilder();

        for(String module : commands.keySet()) {
            ArrayList<String> lines = commands.get(module);

            description.append("**").append(module).append(" Commands**\n");
            for(String line : lines) {
                description.append(line).append("\n");
            }
        }

        if(event == null) {
            new CommandResponse("**Available Commands**\n" + description).send();
        } else {

            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Available Commands");
            embedBuilder.setDescription(description.toString());
            new CommandResponse(embedBuilder).send(event.getTextChannel());
        }
        return true;
    }
}
