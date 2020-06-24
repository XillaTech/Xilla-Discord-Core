package net.xilla.discordcore.staff.cmd;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.permission.user.PermissionUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.permission.DiscordUser;
import net.xilla.discordcore.command.response.CoreCommandResponder;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.platform.CoreSettings;

import java.util.Collections;

public class StaffCommand extends CoreObject {

    private CoreCommandExecutor executor;

    // String name, String[] activators, String module, String description, String usage, int staffLevel
    public StaffCommand() {
        this.executor = (name, args, data) -> {

            PermissionUser user = data.getUser();
            if(user.hasPermission("staff.admin")) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("groups")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("create")) {

                            } else if (args[1].equalsIgnoreCase("delete")) {

                            } else if (args[1].equalsIgnoreCase("edit")) {

                            } else if (args[1].equalsIgnoreCase("info")) {

                            } else if (args[1].equalsIgnoreCase("list")) {

                            }
                        }
                    } else if (args[0].equalsIgnoreCase("departments")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("create")) {

                            } else if (args[1].equalsIgnoreCase("delete")) {

                            } else if (args[1].equalsIgnoreCase("edit")) {

                            } else if (args[1].equalsIgnoreCase("info")) {

                            } else if (args[1].equalsIgnoreCase("list")) {

                            }
                        }
                    }
                }

                CoreSettings settings = DiscordCore.getInstance().getSettings();
                String description = "*Available Commands*\n"
                        + settings.getCommandPrefix() + "s groups - Manage staff groups\n"
                        + settings.getCommandPrefix() + "s departments - Manage staff departments\n";

                EmbedBuilder builder = new EmbedBuilder().setTitle("Staff");
                builder.setDescription(description);

                return new CoreCommandResponse(data).setEmbed(builder.build());
            } else {
                EmbedBuilder builder = new EmbedBuilder().setTitle("Error!");
                builder.setDescription("You do not have permission for this command.");

                return new CoreCommandResponse(data).setEmbed(builder.build());
            }
        };
    }

    public Command build() {
        return new Command("Core", "Staff", new String[] {"staff", "s"}, "staff", "View and manage your staff", Collections.singletonList(executor));
    }

}
