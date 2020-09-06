package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.staff.Group;

import java.util.ArrayList;
import java.util.List;

public class StaffCommand extends CoreObject {

    public StaffCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Core", "Staff");
        commandBuilder.setActivators("staff", "s");
        commandBuilder.setDescription("View and manage your staff");
        commandBuilder.setPermission("core.staff");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CoreCommandExecutor getExecutor() {
        return (data) -> {
            StringBuilder description = new StringBuilder();

            MessageReceivedEvent event = null;
            if(data.get() instanceof  MessageReceivedEvent) {
                event = (MessageReceivedEvent)data.get();
            }

            if (data.getArgs().length >= 3 && data.getArgs()[0].equalsIgnoreCase("create")) {
                // String groupName, String groupID, String serverID, ArrayList<String> permissions

                if(event != null) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < data.getArgs().length; i++) {
                        builder.append(data.getArgs()[i]);
                        if (i != data.getArgs().length - 1) {
                            builder.append(" ");
                        }
                    }
                    Role role = getRole(data.getArgs()[1]);
                    if(role != null) {
                        Group group = new Group(role.getId(), builder.toString(), role.getGuild().getId(), new ArrayList<>());
                        getGroupManager().addObject(group);
                        getGroupManager().save();
                        description.append("That group has been created!");
                    } else {
                        description.append("That is not a valid discord role!");
                    }
                } else {
                    if(data.getArgs().length >= 4) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 3; i < data.getArgs().length; i++) {
                            builder.append(data.getArgs()[i]);
                            if (i != data.getArgs().length - 1) {
                                builder.append(" ");
                            }
                        }
                        Role role = getRole(data.getArgs()[1]);
                        if(role != null) {
                            Group group = new Group(role.getId(), builder.toString(), role.getGuild().getId(), new ArrayList<>());
                            getGroupManager().addObject(group);
                            getGroupManager().save();
                            description.append("That group has been created!");
                        } else {
                            description.append("That is not a valid discord role!");
                        }
                    } else {
                        description.append(getPrefix()).append("s create <role id> <name>");
                    }
                }

            } else if (data.getArgs().length == 2 && data.getArgs()[0].equalsIgnoreCase("delete")) {
                Group group;
                if(event == null) {
                    group= getGroup(null, data.getArgs()[1]);
                } else {
                    group = getGroup(event.getGuild(), data.getArgs()[1]);
                }
                if(group != null && (event == null || group.getGroupID().equalsIgnoreCase(event.getAuthor().getId()))) {
                    getGroupManager().removeObject(group.getKey());
                    getGroupManager().save();
                    description.append("That group has been removed!");
                } else {
                    description.append("That is not a valid group!");
                }
            } else if (data.getArgs().length > 3 && data.getArgs()[0].equalsIgnoreCase("addpermission")) {
                Group group;
                if(event == null) {
                    group= getGroup(null, data.getArgs()[1]);
                } else {
                    group = getGroup(event.getGuild(), data.getArgs()[1]);
                }
                if(group != null && (event == null || group.getGroupID().equalsIgnoreCase(event.getAuthor().getId()))) {
                    group.addPermission(data.getArgs()[2]);
                    getGroupManager().save();
                    description.append("That permission has been added!");
                } else {
                    description.append("That is not a valid group!");
                }
            } else if (data.getArgs().length > 3 && data.getArgs()[0].equalsIgnoreCase("removepermission")) {
                Group group;
                if(event == null) {
                    group = getGroup(null, data.getArgs()[1]);
                } else {
                    group = getGroup(event.getGuild(), data.getArgs()[1]);
                }
                if(group != null && (event == null || group.getGroupID().equalsIgnoreCase(event.getAuthor().getId()))) {
                    group.removePermission(data.getArgs()[2]);
                    getGroupManager().save();
                    description.append("That permission has been removed!");
                } else {
                    description.append("That is not a valid group!");
                }
            } else if (data.getArgs().length == 2 && data.getArgs()[0].equalsIgnoreCase("info")) {
                Group group;
                if(event == null) {
                    group= getGroup(null, data.getArgs()[1]);
                } else {
                    group = getGroup(event.getGuild(), data.getArgs()[1]);
                }
                if(group != null && (event == null || group.getServerID().equalsIgnoreCase(event.getGuild().getId()))) {
                    description = new StringBuilder("Name: " + group.getName() + "\n" +
                            "Role: <@&" + group.getGroupID() + ">\n" +
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
            } else if (data.getArgs().length == 1 && data.getArgs()[0].equalsIgnoreCase("list")) {
                List<Group> groups;
                if(event != null) {
                    groups = DiscordCore.getInstance().getGroupManager().getGroupsByServer(event.getGuild().getId());
                } else {
                    groups = DiscordCore.getInstance().getGroupManager().getList();
                }

                if(groups.size() > 0) {
                    description = new StringBuilder("Available Groups: `");

                    for(int i = 0; i < groups.size(); i++) {
                        description.append(groups.get(i).getName()).append(" (").append(groups.get(i).getGroupID()).append(")");
                        if (i == groups.size() - 1) {
                            description.append("`");
                        } else {
                            description.append(", ");
                        }
                    }
                } else {
                    description = new StringBuilder("Available Groups: None");
                }
            } else {
                description = new StringBuilder("*Available Commands*\n"
                        + getCoreSetting().getCommandPrefix() + "s create <@role> <group name> - Create staff group\n"
                        + getCoreSetting().getCommandPrefix() + "s delete <group name/@role> - Delete a staff group\n"
                        + getCoreSetting().getCommandPrefix() + "s addpermission <@role> <permission> - Add a permission\n"
                        + getCoreSetting().getCommandPrefix() + "s removepermission <@role> <permission> - Remove a permission\n"
                        + getCoreSetting().getCommandPrefix() + "s info <group name/@role> - View a staff group\n"
                        + getCoreSetting().getCommandPrefix() + "s list - List staff groups\n");
            }

            EmbedBuilder builder = new EmbedBuilder().setTitle("Staff");
            builder.setDescription(description.toString());
            builder.setColor(getColor());

            return new CoreCommandResponse(data).setEmbed(builder.build());
        };
    }

}
