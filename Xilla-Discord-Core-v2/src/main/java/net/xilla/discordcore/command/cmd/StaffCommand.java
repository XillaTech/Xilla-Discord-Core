package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.staff.Group;

import java.awt.*;

public class StaffCommand extends CoreObject {

    public StaffCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Core", "Staff");
        commandBuilder.setModule("Core");
        commandBuilder.setName("Staff");
        commandBuilder.setActivators("staff", "s");
        commandBuilder.setDescription("View and manage your staff");
        commandBuilder.setPermission("core.staff");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CoreCommandExecutor getExecutor() {
        return (data) -> {

            StringBuilder description = new StringBuilder("*Available Commands*\n"
                    + getCoreSetting().getCommandPrefix() + "s groups - Manage staff groups\n"
                    + getCoreSetting().getCommandPrefix() + "s departments - Manage staff departments\n");

            if (data.getArgs().length > 0) {
                if (data.getArgs()[0].equalsIgnoreCase("groups")) {
                    if (data.getArgs().length > 1) {
                        if (data.getArgs()[1].equalsIgnoreCase("create")) {

                        } else if (data.getArgs()[1].equalsIgnoreCase("delete")) {

                        } else if (data.getArgs()[1].equalsIgnoreCase("edit")) {

                        } else if (data.getArgs()[1].equalsIgnoreCase("info")) {
                            if(data.getArgs().length == 3) {
                                Group group = getGroupManager().getGroup(data.getArgs()[2]);
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
                        } else if (data.getArgs()[1].equalsIgnoreCase("list")) {
                            description = new StringBuilder("Available Groups: `");
                            for(int i = 0; i < getGroupManager().getList().size(); i++) {
                                Group group = getGroupManager().getList().get(i);
                                if(i == getGroupManager().getList().size() - 1) {
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
                } else if (data.getArgs()[0].equalsIgnoreCase("departments")) {
                    if (data.getArgs().length > 1) {
                        if (data.getArgs()[1].equalsIgnoreCase("create")) {

                        } else if (data.getArgs()[1].equalsIgnoreCase("delete")) {

                        } else if (data.getArgs()[1].equalsIgnoreCase("edit")) {

                        } else if (data.getArgs()[1].equalsIgnoreCase("info")) {

                        } else if (data.getArgs()[1].equalsIgnoreCase("list")) {

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
