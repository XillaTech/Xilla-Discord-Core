package net.xilla.discordcore.core.permission;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.command.permission.user.PermissionUser;
import net.xilla.discordcore.core.permission.user.DiscordUser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PermissionAPI {

    @Getter
    private static Map<String, DiscordUser> userCache = new ConcurrentHashMap<>();

    public static boolean hasPermission(Member member, String permission) {
        if(member == null) {
            return false;
        }

        DiscordUser user = getUser(member);

        if(user == null) {
            return false;
        }

        return user.hasPermission(member.getGuild(), permission);
    }

    public static boolean hasPermission(Guild guild, PermissionUser user, String permission) {
        if(user == null) {
            return false;
        }

        return user.hasPermission(guild, permission);
    }

    public static DiscordUser getUser(Member member) {
        if(member != null) {
            if(userCache.containsKey(member.getId())) {
                return userCache.get(member.getId());
            }

            Manager<String, DiscordUser> manager = DiscordCore.getInstance().getUserManager().getManager(member.getGuild());

            DiscordUser user = manager.get(member.getId());
            if (user != null) {
                return user;
            }

            user = new DiscordUser(member);
            Logger.log(LogLevel.DEBUG, "Unable to find discord user information for " + member + " so I will create new data for them!", PermissionAPI.class);

            userCache.put(member.getId(), user);

            return user;
        }
        return null;
    }

}
