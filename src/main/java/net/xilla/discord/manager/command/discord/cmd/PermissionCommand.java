package net.xilla.discord.manager.command.discord.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.api.command.CommandExecutor;
import net.xilla.discord.api.embed.EmbedFormat;
import net.xilla.discord.api.embed.EmbedProcessor;
import net.xilla.discord.api.permission.GroupProcessor;
import net.xilla.discord.api.permission.PermissionGroup;
import net.xilla.discord.api.permission.PermissionUser;
import net.xilla.discord.api.permission.UserProcessor;
import net.xilla.discord.manager.command.discord.DiscordCommand;
import net.xilla.discord.manager.command.discord.executors.SectionSelector;
import net.xilla.discord.manager.command.discord.executors.SendEmbed;
import net.xilla.discord.manager.command.discord.executors.SendMessage;
import net.xilla.discord.manager.embed.DiscordEmbed;
import net.xilla.discord.manager.embed.template.PermissionTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PermissionCommand extends DiscordCommand {

    public PermissionCommand() {
        super("Perms", "Manage the bots permissions", "core.help", Arrays.asList("permissions", "perms"), get());
    }

    private static CommandExecutor get() {
        return (input) -> {
            SectionSelector selector = new SectionSelector("Permission", 0);

            selector.put("user", "Control the user's permissions.", getUsers());
            selector.put("group", "Control the group's permissions.", getGroups());

            selector.run(input);
        };
    }

    private static CommandExecutor getUsers() {
        return (input) -> {
            SectionSelector selector = new SectionSelector("Permission", 1);

            selector.put("add", "Add a permission", addPermission(false));
            selector.put("remove", "Remove a permission", removePermission(false));
            selector.put("info", "Gives all the users info", getInfo(false));

            selector.run(input);
        };
    }

    private static CommandExecutor getGroups() {
        return (input) -> {
            SectionSelector selector = new SectionSelector("Permission", 1);

            selector.put("add", "Add a permission", addPermission(true));
            selector.put("remove", "Remove a permission", removePermission(true));
            selector.put("info", "Gives all the groups info", getInfo(true));
            selector.put("list", "Lists all available groups", listGroups());

            selector.run(input);
        };
    }

    private static CommandExecutor addPermission(boolean isGroup) {
        return (input) -> {
            StringBuilder response = new StringBuilder();
            if(isGroup) {
                GroupProcessor groupProcessor = DiscordCore.getInstance().getGroupProcessor();
                if(input.getArgs().length >= 4) {
                    String permission = input.getArgs()[2];

                    String[] nameArgs = Arrays.copyOfRange(input.getArgs(), 3, input.getArgs().length);
                    String name = String.join(" ", nameArgs);

                    Role role = null;
                    try {
                        role = DiscordCore.getInstance().getJda().getRoleById(name.replace("<@&", "").replace(">", ""));
                    } catch (NumberFormatException ignored) {}
                    if(role == null) {
                        List<Role> roles = DiscordCore.getInstance().getJda().getRolesByName(name, false);
                        if(roles.size() == 0) {
                            roles = DiscordCore.getInstance().getJda().getRolesByName(name, true);
                        }

                        if(roles.size() > 0) {
                            role = roles.get(0);
                        }
                    }
                    if(role == null) {
                        response.append("That group does not exist.");
                    } else {
                        PermissionGroup group = groupProcessor.pull(role);
                        group.getPermissions().add(permission);
                        response.append("You have added the permission `").append(permission).append("` to the group ");
                        response.append(role.getAsMention());
                        groupProcessor.save();
                    }
                } else {
                    response.append(DiscordCore.getInstance().getSettings().getPrefix());
                    response.append("perms group add (permission) (group id)");
                }
            } else {
                UserProcessor userProcessor = DiscordCore.getInstance().getUserProcessor();
                if(input.getArgs().length >= 4) {
                    String permission = input.getArgs()[2];

                    String[] nameArgs = Arrays.copyOfRange(input.getArgs(), 3, input.getArgs().length);
                    String name = String.join(" ", nameArgs);

                    User user = null;
                    try {
                        user = DiscordCore.getInstance().getJda().getUserById(name.replace("<@!", "").replace(">", ""));
                    } catch (NumberFormatException ignored) {}
                    if(user == null) {
                        List<User> users = DiscordCore.getInstance().getJda().getUsersByName(name, false);
                        if(users.size() == 0) {
                            users = DiscordCore.getInstance().getJda().getUsersByName(name, true);
                        }

                        if(users.size() > 0) {
                            user = users.get(0);
                        }
                    }
                    if(user == null) {
                        response.append("That user does not exist.");
                    } else {
                        Member member = input.getEvent().getGuild().getMember(user);
                        if(member == null) {
                            response.append("That user is not a member of this guild.");
                        } else {
                            PermissionUser permissionUser = userProcessor.pull(member);
                            permissionUser.getPermissions().add(permission);
                            response.append("You have added the permission `").append(permission).append("` to the user ");
                            response.append(member.getAsMention());
                            userProcessor.save();
                        }
                    }
                } else {
                    response.append(DiscordCore.getInstance().getSettings().getPrefix());
                    response.append("perms group add (permission) (user id)");
                }
            }

            EmbedProcessor embedProcessor = DiscordCore.getInstance().getEmbedProcessor();
            EmbedFormat embedFormat = embedProcessor.get("Permission");

            SendEmbed sendEmbed = new SendEmbed(
                    new EmbedBuilder(
                            embedFormat.buildTemplate(
                                    input.getEvent().getMember()
                            )));
            sendEmbed.getEmbedBuilder().setDescription(response);
            sendEmbed.run(input);
        };
    }

    private static CommandExecutor removePermission(boolean isGroup) {
        return (input) -> {
            StringBuilder response = new StringBuilder();
            if(isGroup) {
                GroupProcessor groupProcessor = DiscordCore.getInstance().getGroupProcessor();
                if(input.getArgs().length >= 4) {
                    String permission = input.getArgs()[2];

                    String[] nameArgs = Arrays.copyOfRange(input.getArgs(), 3, input.getArgs().length);
                    String name = String.join(" ", nameArgs);

                    Role role = null;
                    try {
                        role = DiscordCore.getInstance().getJda().getRoleById(name.replace("<@&", "").replace(">", ""));
                    } catch (NumberFormatException ignored) {}
                    if(role == null) {
                        List<Role> roles = DiscordCore.getInstance().getJda().getRolesByName(name, false);
                        if(roles.size() == 0) {
                            roles = DiscordCore.getInstance().getJda().getRolesByName(name, true);
                        }

                        if(roles.size() > 0) {
                            role = roles.get(0);
                        }
                    }
                    if(role == null) {
                        response.append("That group does not exist.");
                    } else {
                        PermissionGroup group = groupProcessor.pull(role);
                        group.getPermissions().remove(permission);
                        response.append("You have removed the permission `").append(permission).append("` from the group ");
                        response.append(role.getAsMention());
                        groupProcessor.save();
                    }
                } else {
                    response.append(DiscordCore.getInstance().getSettings().getPrefix());
                    response.append("perms group add (permission) (group id)");
                }
            } else {
                UserProcessor userProcessor = DiscordCore.getInstance().getUserProcessor();
                if(input.getArgs().length >= 4) {
                    String permission = input.getArgs()[2];

                    String[] nameArgs = Arrays.copyOfRange(input.getArgs(), 3, input.getArgs().length);
                    String name = String.join(" ", nameArgs);

                    User user = null;
                    try {
                        user = DiscordCore.getInstance().getJda().getUserById(name.replace("<@!", "").replace(">", ""));
                    } catch (NumberFormatException ignored) {}
                    if(user == null) {
                        List<User> users = DiscordCore.getInstance().getJda().getUsersByName(name, false);
                        if(users.size() == 0) {
                            users = DiscordCore.getInstance().getJda().getUsersByName(name, true);
                        }

                        if(users.size() > 0) {
                            user = users.get(0);
                        }
                    }
                    if(user == null) {
                        response.append("That user does not exist.");
                    } else {
                        Member member = input.getEvent().getGuild().getMember(user);
                        if(member == null) {
                            response.append("That user is not a member of this guild.");
                        } else {
                            PermissionUser permissionUser = userProcessor.pull(member);
                            permissionUser.getPermissions().remove(permission);
                            response.append("You have removed the permission `").append(permission).append("` from the user ");
                            response.append(member.getAsMention());
                            userProcessor.save();
                        }
                    }
                } else {
                    response.append(DiscordCore.getInstance().getSettings().getPrefix());
                    response.append("perms group add (permission) (user id)");
                }
            }

            EmbedProcessor embedProcessor = DiscordCore.getInstance().getEmbedProcessor();
            EmbedFormat embedFormat = embedProcessor.get("Permission");

            SendEmbed sendEmbed = new SendEmbed(
                    new EmbedBuilder(
                            embedFormat.buildTemplate(
                                    input.getEvent().getMember()
                            )));
            sendEmbed.getEmbedBuilder().setDescription(response);
            sendEmbed.run(input);
        };
    }

    private static CommandExecutor getInfo(boolean isGroup) {
        return (input) -> {
            StringBuilder response = new StringBuilder();
            if(isGroup) {
                GroupProcessor groupProcessor = DiscordCore.getInstance().getGroupProcessor();
                if(input.getArgs().length >= 3) {
                    String[] nameArgs = Arrays.copyOfRange(input.getArgs(), 2, input.getArgs().length);
                    String name = String.join(" ", nameArgs);

                    Role role = null;
                    try {
                        role = DiscordCore.getInstance().getJda().getRoleById(name.replace("<@&", "").replace(">", ""));
                    } catch (NumberFormatException ignored) {}
                    if(role == null) {
                        List<Role> roles = DiscordCore.getInstance().getJda().getRolesByName(name, false);
                        if(roles.size() == 0) {
                            roles = DiscordCore.getInstance().getJda().getRolesByName(name, true);
                        }

                        if(roles.size() > 0) {
                            role = roles.get(0);
                        }
                    }
                    if(role == null) {
                        response.append("That group does not exist.");
                    } else {
                        PermissionGroup group = groupProcessor.pull(role);
                        response.append("Name: ").append(role.getName()).append("\n");
                        response.append("ID: ").append(role.getId()).append("\n");
                        response.append("Permissions: `").append(String.join(", ", group.getPermissions())).append("`");
                    }
                } else {
                    response.append(DiscordCore.getInstance().getSettings().getPrefix());
                    response.append("perms group info (group id)");
                }
            } else {
                UserProcessor userProcessor = DiscordCore.getInstance().getUserProcessor();
                if(input.getArgs().length >= 3) {
                    String[] nameArgs = Arrays.copyOfRange(input.getArgs(), 2, input.getArgs().length);
                    String name = String.join(" ", nameArgs);

                    User user = null;
                    try {
                        user = DiscordCore.getInstance().getJda().getUserById(name.replace("<@!", "").replace(">", ""));
                    } catch (NumberFormatException ignored) {}
                    if(user == null) {
                        List<User> users = DiscordCore.getInstance().getJda().getUsersByName(name, false);
                        if(users.size() == 0) {
                            users = DiscordCore.getInstance().getJda().getUsersByName(name, true);
                        }

                        if(users.size() > 0) {
                            user = users.get(0);
                        }
                    }
                    if(user == null) {
                        response.append("That user does not exist.");
                    } else {
                        Member member = input.getEvent().getGuild().getMember(user);
                        if(member == null) {
                            response.append("That user is not a member of this guild.");
                        } else {
                            PermissionUser permissionUser = userProcessor.pull(member);
                            response.append("Name: ").append(user.getName()).append("\n");
                            response.append("ID: ").append(user.getId()).append("\n");
                            response.append("Permissions: `").append(String.join(", ", permissionUser.getPermissions())).append("`");
                        }
                    }
                } else {
                    response.append(DiscordCore.getInstance().getSettings().getPrefix());
                    response.append("perms group info (user id)");
                }
            }

            EmbedProcessor embedProcessor = DiscordCore.getInstance().getEmbedProcessor();
            EmbedFormat embedFormat = embedProcessor.get("Permission");

            SendEmbed sendEmbed = new SendEmbed(
                    new EmbedBuilder(
                            embedFormat.buildTemplate(
                                    input.getEvent().getMember()
                            )));
            sendEmbed.getEmbedBuilder().setDescription(response);
            sendEmbed.run(input);
        };
    }

    private static CommandExecutor listGroups() {
        return (input) -> {
            StringBuilder response = new StringBuilder();

            List<String> groups = new ArrayList<>();
            GroupProcessor groupProcessor = DiscordCore.getInstance().getGroupProcessor();
            for(PermissionGroup group : groupProcessor.listObjects()) {
                Role role = DiscordCore.getInstance().getJda().getRoleById(group.getId());
                if(role == null) {
                    groups.add(group.getId());
                } else {
                    groups.add(role.getAsMention());
                }
            }

            response.append("Groups: ");
            if(groups.size() > 0) {
                response.append(String.join(", ", groups));
            } else {
                response.append("None");
            }

            EmbedProcessor embedProcessor = DiscordCore.getInstance().getEmbedProcessor();
            EmbedFormat embedFormat = embedProcessor.get("Permission");

            SendEmbed sendEmbed = new SendEmbed(
                    new EmbedBuilder(
                        embedFormat.buildTemplate(
                                input.getEvent().getMember()
            )));
            sendEmbed.getEmbedBuilder().setDescription(response);
            sendEmbed.run(input);
        };
    }

}
