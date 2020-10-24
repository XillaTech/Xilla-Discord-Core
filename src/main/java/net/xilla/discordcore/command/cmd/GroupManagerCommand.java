package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.CoreCommandExecutor;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.permission.PermissionAPI;
import net.xilla.discordcore.core.permission.group.DiscordGroup;

import java.util.ArrayList;

public class GroupManagerCommand extends CoreObject {

    public GroupManagerCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Admin", "GroupManager", true);
        commandBuilder.setActivators("groupmanager", "gm");
        commandBuilder.setDescription("View and manage your groups");
        commandBuilder.setPermission("staff.admin");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CoreCommandExecutor getExecutor() {
        return (data) -> {

            StringBuilder description = null;

            MessageReceivedEvent event = null;
            if(data.get() instanceof  MessageReceivedEvent) {
                event = (MessageReceivedEvent)data.get();
            }

            Manager<DiscordGroup> manager;

            int getData = 0;
            if(event == null) {
                getData = 1;
                if(data.getArgs().length > 0) {
                    Guild guild = getGuild(data.getArgs()[0]);

                    if(guild == null) {
                        return new CoreCommandResponse(data).setDescription("That is not a valid guild!");
                    }
                    manager = getPlatform().getGroupManager().getManager(guild);
                } else {
                    manager = null;
                }
            } else {
                manager = getPlatform().getGroupManager().getManager(event.getGuild());
            }

            Guild guild;
            if(getData == 0) {
                guild = event.getGuild();
            } else if(data.getArgs().length > 0) {
                guild = getGuild(data.getArgs()[0]);
            } else {
                guild = null;
            }

            if(data.getArgs().length > 0 && manager == null) {
                return new CoreCommandResponse(data).setDescription("That is not a valid guild!");
            }

            if (data.getArgs().length >= 3 + getData && data.getArgs()[getData].equalsIgnoreCase("create")) {
                description = new StringBuilder();

                Role role = getRole(data.getArgs()[1 + getData]);
                if(role == null) {
                    description.append("That role is not valid!");
                } else {
                    StringBuilder name = new StringBuilder();
                    for(int i = 2 + getData; i < data.getArgs().length; i++) {
                        name.append(data.getArgs()[i]);
                        if(i != data.getArgs().length - 1) {
                            name.append(" ");
                        }
                    }

                    DiscordGroup group = new DiscordGroup(role.getId(), name.toString(), guild.getId(), new ArrayList<>());
                    manager.put(group);
                    manager.save();
                    description.append("That role has been successfully created!");
                }
            } else if (data.getArgs().length >= 2 + getData && data.getArgs()[getData].equalsIgnoreCase("delete")) {
                description = new StringBuilder();

                StringBuilder name = new StringBuilder();
                for(int i = 1 + getData; i < data.getArgs().length; i++) {
                    name.append(data.getArgs()[i]);
                    if(i != data.getArgs().length - 1) {
                        name.append(" ");
                    }
                }

                DiscordGroup group = getGroup(guild, name.toString());
                if(group == null) {
                    description.append("That is not a valid group!");
                } else {
                    manager.remove(group);
                    manager.save();
                    description.append("That group has been removed!");
                }
            } else if (data.getArgs().length >= 2 + getData && data.getArgs()[getData].equalsIgnoreCase("info")) {
                description = new StringBuilder();

                StringBuilder name = new StringBuilder();
                for(int i = 1 + getData; i < data.getArgs().length; i++) {
                    name.append(data.getArgs()[i]);
                    if(i != data.getArgs().length - 1) {
                        name.append(" ");
                    }
                }

                DiscordGroup group = getGroup(guild, name.toString());
                if(group == null) {
                    description.append("That is not a valid group!");
                } else {
                    description.append("**Name:** ").append(group.getName()).append("\n");
                    description.append("**Role ID:** ").append(group.getGroupID()).append("\n");
                    description.append("**Server ID:** ").append(group.getServerID()).append("\n");
                    description.append("**Permissions:** \n");

                    for(String perm : group.getPermissions()) {
                        description.append("> ").append(perm).append("\n");
                    }
                }

            } else if (data.getArgs().length >= 3 + getData && data.getArgs()[getData].equalsIgnoreCase("addpermission")) {
                description = new StringBuilder();

                StringBuilder name = new StringBuilder();
                for(int i = 2 + getData; i < data.getArgs().length; i++) {
                    name.append(data.getArgs()[i]);
                    if(i != data.getArgs().length - 1) {
                        name.append(" ");
                    }
                }

                DiscordGroup group = getGroup(guild, name.toString());
                if(group == null) {
                    description.append("That is not a valid group!");
                } else {
                    String permission = data.getArgs()[1];
                    if(permission.toLowerCase().startsWith("core.")) {
                        if(!PermissionAPI.hasPermission(event.getMember(), "core.admin")) {
                            description.append("You do not have permission (core.admin) to add that permission!");
                        }
                    }

                    if(description.length() == 0) {
                        group.addPermission(permission);
                        manager.save();
                        description.append("That permission has been added!");
                    }

                }
            } else if (data.getArgs().length >= 3 + getData && data.getArgs()[getData].equalsIgnoreCase("removepermission")) {
                description = new StringBuilder();

                StringBuilder name = new StringBuilder();
                for(int i = 2 + getData; i < data.getArgs().length; i++) {
                    name.append(data.getArgs()[i]);
                    if(i != data.getArgs().length - 1) {
                        name.append(" ");
                    }
                }

                DiscordGroup group = getGroup(guild, name.toString());
                if(group == null) {
                    description.append("That is not a valid group!");
                } else {
                    String permission = data.getArgs()[1 + getData];
                    if(permission.toLowerCase().startsWith("core.")) {
                        if(!PermissionAPI.hasPermission(event.getMember(), "core.admin")) {
                            description.append("You do not have permission (core.admin) to remove that permission!");
                        }
                    }

                    if(description.length() == 0) {
                        group.removePermission(permission);
                        manager.save();
                        description.append("That permission has been remove!");
                    }

                }
            } else if (data.getArgs().length == 1 + getData && data.getArgs()[getData].equalsIgnoreCase("list")) {
                description = new StringBuilder();

                description.append("Groups: ");

                ArrayList<DiscordGroup> groups = new ArrayList<>(manager.getData().values());
                for(int i = 0; i < groups.size(); i++) {
                    description.append(groups.get(i).getName()).append(" (").append(groups.get(i).getKey()).append(")");

                    if(i != groups.size() - 1) {
                        description.append(", ");
                    }
                }
            }

            if(description == null) {
                if(getData == 0) {
                    description = new StringBuilder("*Available Commands*\n"
                            + getCoreSetting().getCommandPrefix() + "gm create <@role> <group name> - Create staff group\n"
                            + getCoreSetting().getCommandPrefix() + "gm delete <group name/@role> - Delete a staff group\n"
                            + getCoreSetting().getCommandPrefix() + "gm addpermission <permission> <group name/@role> - Add a permission\n"
                            + getCoreSetting().getCommandPrefix() + "gm removepermission <permission> <group name/@role> - Remove a permission\n"
                            + getCoreSetting().getCommandPrefix() + "gm info <group name/@role> - View a staff group\n"
                            + getCoreSetting().getCommandPrefix() + "gm list - List staff groups\n");
                } else {
                    description = new StringBuilder("*Available Commands*\n"
                            + getCoreSetting().getCommandPrefix() + "gm <guild> create <@role> <group name> - Create staff group\n"
                            + getCoreSetting().getCommandPrefix() + "gm <guild> delete <group name/@role> - Delete a staff group\n"
                            + getCoreSetting().getCommandPrefix() + "gm <guild> addpermission <group name/@role> <permission> - Add a permission\n"
                            + getCoreSetting().getCommandPrefix() + "gm <guild> removepermission <group name/@role> <permission> - Remove a permission\n"
                            + getCoreSetting().getCommandPrefix() + "gm <guild> info <group name/@role> - View a staff group\n"
                            + getCoreSetting().getCommandPrefix() + "gm <guild> list - List staff groups\n");
                }
            }

            EmbedBuilder builder = new EmbedBuilder().setTitle("Staff");
            builder.setDescription(description.toString());
            builder.setColor(getColor());

            return new CoreCommandResponse(data).setEmbed(builder.build());
        };
    }

}
