package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.permission.PermissionAPI;
import net.xilla.discordcore.core.permission.group.DiscordGroup;
import net.xilla.discordcore.core.permission.user.DiscordUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManagerCommand extends CoreObject {

    public UserManagerCommand() {
        CommandBuilder builder = new CommandBuilder("Admin", "UserManager", true);
        builder.setActivators("usermanager", "um");
        builder.setDescription("Manage your users permissions.");
        builder.setCommandExecutor(data -> {
           StringBuilder response = null;

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

           if(data.getArgs().length >= 2 + getData && data.getArgs()[getData].equalsIgnoreCase("view")) {
               response = new StringBuilder();

               StringBuilder name = new StringBuilder();
               for(int i = 1 + getData; i < data.getArgs().length; i++) {
                   name.append(data.getArgs()[i]);
                   if(i != data.getArgs().length - 1) {
                       name.append(" ");
                   }
               }

               Member member = getMember(guild, name.toString());
               if(member == null) {
                   response.append("That member is not valid!");
               } else {
                   DiscordUser user = PermissionAPI.getUser(member);

                   response.append("**Name:** ").append(member.getAsMention()).append("\n");
                   response.append("**ID:** ").append(member.getId()).append("\n");
                   response.append("**Groups:** ");

                   HashMap<String, List<String>> permissionCache = new HashMap<>();
                   for (int i = 0; i < user.getGroups().size(); i++) {
                       String id = user.getGroups().get(i).getIdentifier();

                       response.append(id);

                       for (String perm : user.getGroups().get(i).getPermissions()) {
                           List<String> cache = permissionCache.getOrDefault(id, new ArrayList<>());
                           cache.add(perm);
                           permissionCache.put(id, cache);
                       }

                       if (i != user.getGroups().size() - 1) {
                           response.append(", ");
                       }
                   }

                   response.append("\n**Permissions:** \n");

                   for(String key : permissionCache.keySet()) {
                       List<String> perms = permissionCache.get(key);

                       for(String perm : perms) {
                           response.append("> ").append(perm).append(" (From ").append(key).append(")\n");
                       }
                   }

                   for(String perm : user.getPermissions()) {
                       response.append("> ").append(perm).append("\n");
                   }
               }
           } else if(data.getArgs().length >= 3 + getData && data.getArgs()[getData].equalsIgnoreCase("add")) {
               response = new StringBuilder();

               String permission = data.getArgs()[1 + getData];

               StringBuilder name = new StringBuilder();
               for(int i = 2 + getData; i < data.getArgs().length; i++) {
                   name.append(data.getArgs()[i]);
                   if(i != data.getArgs().length - 1) {
                       name.append(" ");
                   }
               }

               Member member = getMember(guild, name.toString());
               if(member == null) {
                   response.append("That member is not valid!");
               } else {
                   DiscordUser user = PermissionAPI.getUser(member);

                   if(permission.toLowerCase().startsWith("core.")) {
                       if(!user.hasPermission("core.admin")) {
                           response.append("You do not have the permission (core.admin) to add that permission.");
                       }
                   }

                   if(response.length() == 0) {
                       user.getPermissions().add(permission.toLowerCase());
                       manager.save();
                       response.append("You have added that permission to ").append(member.getAsMention()).append("!");
                   }
               }
           } else if(data.getArgs().length >= 3 + getData && data.getArgs()[getData].equalsIgnoreCase("remove")) {
               response = new StringBuilder();

               String permission = data.getArgs()[1 + getData];

               StringBuilder name = new StringBuilder();
               for(int i = 2 + getData; i < data.getArgs().length; i++) {
                   name.append(data.getArgs()[i]);
                   if(i != data.getArgs().length - 1) {
                       name.append(" ");
                   }
               }

               Member member = getMember(guild, name.toString());
               if(member == null) {
                   response.append("That member is not valid!");
               } else {
                   DiscordUser user = PermissionAPI.getUser(member);

                   if(permission.toLowerCase().startsWith("core.")) {
                       if(!user.hasPermission("core.admin")) {
                           response.append("You do not have the permission (core.admin) to remove that permission.");
                       }
                   }

                   if(response.length() == 0) {
                       user.getPermissions().remove(permission.toLowerCase());
                       manager.save();
                       response.append("You have removed that permission from ").append(member.getAsMention()).append("!");
                   }
               }
           }

           if(response == null) {
               response = new StringBuilder();

               if(getData == 0) {
                   response.append(getPrefix()).append("um view <user> - View a users permissions\n");
                   response.append(getPrefix()).append("um add <permission> <user> - Add a permission to a user\n");
                   response.append(getPrefix()).append("um remove <permission> <user> - Remove a permission from a user");
               } else {
                   response.append(getPrefix()).append("um <guild> view <user> - View a users permissions\n");
                   response.append(getPrefix()).append("um <guild> add <permission> <user> - Add a permission to a user\n");
                   response.append(getPrefix()).append("um <guild> remove <permission> <user> - Remove a permission from a user");
               }
           }

           EmbedBuilder embedBuilder = new EmbedBuilder();
           embedBuilder.setTitle("User Manager");
           embedBuilder.setColor(getColor());
           embedBuilder.setDescription(response.toString());
           return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
        });
        builder.build();
    }

}
