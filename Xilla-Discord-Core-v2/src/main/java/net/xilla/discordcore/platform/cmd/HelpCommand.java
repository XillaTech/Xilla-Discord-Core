package net.xilla.discordcore.platform.cmd;

import com.tobiassteely.tobiasapi.api.Log;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.Command;

import java.util.ArrayList;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("Help", new String[] {"help", "?"}, "Core", "Sends this message", "help", 0);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        if(event == null) {
            Log.sendMessage(0, new Data().getLineBreak());
            Log.sendMessage(0, "Commands");
            Log.sendMessage(0, new Data().getLineBreak());
            Log.sendMessage(0, " > help - Opens this menu");
            for(ModuleLoader module : DiscordCore.getInstance().getModuleManager().getModules()) {
                ArrayList<String> commandLines = new ArrayList<>();
                for(CommandObject commandObject : DiscordCore.getInstance().getCommandManager().getCommandsByModule(module.getName())) {
                    commandLines.add(" > " + commandObject.getActivators()[0] + " - " + commandObject.getDescription());
                }
                for(String line : commandLines)
                    Log.sendMessage(0, line);
            }

            Log.sendMessage(0, new Data().getLineBreak());
        } else {
            Config config = DiscordCore.getInstance().getConfigManager().getConfig("settings.json");
            String command = config.getString("commandPrefix");

            EmbedBuilder myEmbed = new Data().createEmbed(config.getString("companyName") + "'s Bot", null);
            //myEmbed.addField("Core Commands",command + "Help *Sends this message*\n", false);

            StaffManager staffManager = DiscordCore.getInstance().getStaffManager();

            ArrayList<String> helpCommandLines = new ArrayList<>();
            for(CommandObject commandObject : DiscordCore.getInstance().getCommandManager().getCommandsByModule("Core")) {
                if(staffManager.isAuthorized(event.getAuthor().getId(), commandObject.getStaffLevel())) {
                    helpCommandLines.add(command + commandObject.getUsage() + " *" + commandObject.getDescription() + "*\n");
                }
            }
            if(helpCommandLines.size() > 0)
                myEmbed.addField("Core Commands", new Data().parseStringListNoDelimiter(0, helpCommandLines), false);

            for(ModuleLoader module : DiscordCore.getInstance().getModuleManager().getModules()) {
                ArrayList<String> commandLines = new ArrayList<>();
                if(DiscordCore.getInstance().getCommandManager().getCommandsByModule(module.getName()).size() > 0) {
                    for (CommandObject commandObject : DiscordCore.getInstance().getCommandManager().getCommandsByModule(module.getName())) {
                        if (staffManager.isAuthorized(event.getAuthor().getId(), commandObject.getStaffLevel())) {
                            commandLines.add(command + commandObject.getUsage() + " *" + commandObject.getDescription() + "*\n");
                        }
                    }
                    if (commandLines.size() > 0)
                        myEmbed.addField(module.getName() + " Commands", new Data().parseStringListNoDelimiter(0, commandLines), false);
                }
            }

            // Sends the embed
            event.getChannel().sendMessage(myEmbed.build()).queue();
        }
        return true;
    }
}
