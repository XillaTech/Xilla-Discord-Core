package net.xilla.discordcore.command.cmd;

import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.permission.user.PermissionUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.platform.CoreSettings;
import net.xilla.discordcore.staff.group.Group;

import java.awt.*;
import java.util.Collections;

public class StaffCommand extends CoreObject {

    public StaffCommand() {
        CommandBuilder commandBuilder = new CommandBuilder();
        commandBuilder.setModule("Core");
        commandBuilder.setName("Staff");
        commandBuilder.setActivators("staff", "s");
        commandBuilder.setDescription("View and manage your staff");
        commandBuilder.setPermission("core.staff");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CoreCommandExecutor getExecutor() {
        return (name, args, data) -> {

            StringBuilder description = new StringBuilder("*Available Commands*\n"
                    + getCoreSetting().getCommandPrefix() + "s groups - Manage staff groups\n"
                    + getCoreSetting().getCommandPrefix() + "s departments - Manage staff departments\n");

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("groups")) {
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("create")) {

                        } else if (args[1].equalsIgnoreCase("delete")) {

                        } else if (args[1].equalsIgnoreCase("edit")) {

                        } else if (args[1].equalsIgnoreCase("info")) {
                            if(args.length == 3) {
                                Group group = getStaffManager().getGroupManager().getGroup(args[2]);
                                if(group != null) {
                                    description = new StringBuilder("Name: " + group.getName() + "\n" +
                                            "Role: <@" + group.getGroupID() + ">\n" +
                                            "Staff Level: " + group.getLevel() + "\n" +
                                            "Permissions: `"
                                    );
                                    for(int i = 0; i < group.getPermissions().size(); i++) {
                                        String line = group.getPermissions().get(i);
                                        description.append(line);
                                        if(i != group.getPermissions().size() - 1) {
                                            description.append(", ");
                                        }
                                    }
                                    description.append("`");
                                } else {
                                    description = new StringBuilder("That is not an available group.");
                                }
                            } else {
                                description = new StringBuilder(getCoreSetting().getCommandPrefix() + "s groups info <group>");
                            }
                        } else if (args[1].equalsIgnoreCase("list")) {
                            description = new StringBuilder("Available Groups: `");
                            for(int i = 0; i < getStaffManager().getGroupManager().getList().size(); i++) {
                                Group group = (Group)getStaffManager().getGroupManager().getList().get(i);
                                if(i == getStaffManager().getGroupManager().getList().size() - 1) {
                                    description.append(group.getIdentifier()).append("`");
                                } else {
                                    description.append(group.getIdentifier()).append(", ");
                                }
                            }
                        } else {
                            description = new StringBuilder("*Available Commands*\n"
                                    + getCoreSetting().getCommandPrefix() + "s groups create <group> - Create staff group\n"
                                    + getCoreSetting().getCommandPrefix() + "s groups delete <group> - Delete a staff group\n"
                                    + getCoreSetting().getCommandPrefix() + "s groups edit <group> - Edit a staff group\n"
                                    + getCoreSetting().getCommandPrefix() + "s groups info <group> - View a staff group\n"
                                    + getCoreSetting().getCommandPrefix() + "s groups list - List staff groups\n");
                        }
                    } else {
                        description = new StringBuilder("*Available Commands*\n"
                                + getCoreSetting().getCommandPrefix() + "s groups create <group> - Create staff group\n"
                                + getCoreSetting().getCommandPrefix() + "s groups delete <group> - Delete a staff group\n"
                                + getCoreSetting().getCommandPrefix() + "s groups edit <group> - Edit a staff group\n"
                                + getCoreSetting().getCommandPrefix() + "s groups info <group> - View a staff group\n"
                                + getCoreSetting().getCommandPrefix() + "s groups list - List staff groups\n");
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

            EmbedBuilder builder = new EmbedBuilder().setTitle("Staff");
            builder.setDescription(description.toString());
            builder.setColor(Color.decode(getCoreSetting().getEmbedColor()));

            return new CoreCommandResponse(data).setEmbed(builder.build());
        };
    }

}
