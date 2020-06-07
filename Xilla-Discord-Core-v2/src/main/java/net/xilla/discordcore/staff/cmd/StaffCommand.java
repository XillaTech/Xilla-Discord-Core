package net.xilla.discordcore.staff.cmd;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CoreCommand;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.CoreCommandResponse;
import net.xilla.discordcore.platform.CoreSettings;

public class StaffCommand extends TobiasObject {

    private CoreCommandExecutor executor;

    // String name, String[] activators, String module, String description, String usage, int staffLevel
    public StaffCommand() {
        this.executor = (name, args, inputType, data) -> {
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
            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Staff");
            embedBuilder.setDescription(description);

            CoreCommandResponse response = (CoreCommandResponse)getCommandManager().getResponse();
            response.send(embedBuilder, inputType);
        };
    }

    public Command build() {
        return new CoreCommand("Core", "Staff", new String[] {"staff", "s"}, "staff", "View and manage your staff", 10, executor);
    }

}
