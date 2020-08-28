package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
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
                    Group group = new Group(data.getArgs()[1], builder.toString(), event.getGuild().getId(), new ArrayList<>());
                    getGroupManager().addGroup(group);
                    getGroupManager().save();
                    description.append("That group has been created!");
                } else {
                    if(data.getArgs().length >= 4) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 3; i < data.getArgs().length; i++) {
                            builder.append(data.getArgs()[i]);
                            if (i != data.getArgs().length - 1) {
                                builder.append(" ");
                            }
                        }
                        Group group = new Group(data.getArgs()[1], builder.toString(), data.getArgs()[2], new ArrayList<>());
                        getGroupManager().addGroup(group);
                        getGroupManager().save();
                        description.append("That group has been created!");
                    } else {
                        description.append(getPrefix()).append("s create <role id> <group id> <name>");
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
                    group= getGroup(null, data.getArgs()[1]);
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
                if(group != null && (event == null || group.getGroupID().equalsIgnoreCase(event.getAuthor().getId()))) {
                    description = new StringBuilder("Name: " + group.getName() + "\n" +
                            "Role: <@" + group.getGroupID() + ">\n" +
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

                List<Group> groups = new ArrayList<>();
                for(int i = 0; i < getGroupManager().getList().size(); i++) {
                    Group group = getGroupManager().getList().get(i);
                    if(event == null || group.getGroupID().equalsIgnoreCase(event.getAuthor().getId())) {
                        groups.add(group);
                    }
                }

                if(groups.size() > 0) {
                    description = new StringBuilder("Available Groups: `");

                    System.out.println(groups.size());

                    for(int i = 0; i < groups.size(); i++) {
                        if (i == getGroupManager().getList().size() - 1) {
                            description.append(groups.get(i).getIdentifier()).append("`");
                        } else {
                            description.append(groups.get(i).getIdentifier()).append(", ");
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
