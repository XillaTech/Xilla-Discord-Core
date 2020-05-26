package net.xilla.discordcore.staff.cmd;

import com.tobiassteely.tobiasapi.api.Log;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandResponse;
import net.xilla.discordcore.command.type.legacy.LegacyCommand;
import net.xilla.discordcore.platform.CoreSettings;

public class StaffCommand extends LegacyCommand {

    // String name, String[] activators, String module, String description, String usage, int staffLevel
    public StaffCommand() {
        super("Staff", new String[] {"staff", "s"}, "Core", "View and manage your staff", "modules", 10);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        Log.sendMessage(0, "0 - Staff");
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("groups")) {
                if(args.length > 1) {
                    if(args[1].equalsIgnoreCase("create")) {

                    } else if(args[1].equalsIgnoreCase("delete")) {

                    } else if(args[1].equalsIgnoreCase("edit")) {

                    } else if(args[1].equalsIgnoreCase("info")) {

                    } else if(args[1].equalsIgnoreCase("list")) {

                    }
                }
            } else if(args[0].equalsIgnoreCase("departments")) {
                if(args.length > 1) {
                    if(args[1].equalsIgnoreCase("create")) {

                    } else if(args[1].equalsIgnoreCase("delete")) {

                    } else if(args[1].equalsIgnoreCase("edit")) {

                    } else if(args[1].equalsIgnoreCase("info")) {

                    } else if(args[1].equalsIgnoreCase("list")) {

                    }
                }
            }
        }

        CoreSettings settings = DiscordCore.getInstance().getSettings();
        String description = "*Available Commands*\n"
                + settings.getCommandPrefix() + "s groups - Manage staff groups\n"
                + settings.getCommandPrefix() + "s departments - Manage staff departments\n";
        if(event != null) {
            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Staff");
            embedBuilder.setDescription(description);
            new CommandResponse(embedBuilder).send(event.getTextChannel());
        } else {
            new CommandResponse(description).send();
        }
        return true;
    }

}
