package net.xilla.discordcore.core.permission;

import net.dv8tion.jda.api.entities.Member;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.permission.user.DiscordUser;

public class PermissionAPI {

    public static boolean hasPermission(Member member, String permission) {
        DiscordUser user = getUser(member);
        return user.hasPermission(permission);
    }

    public static DiscordUser getUser(Member member) {
        Manager<DiscordUser> manager = DiscordCore.getInstance().getPlatform().getUserManager().getManager(member.getGuild());

        DiscordUser user = manager.get(member.getId());
        if(user != null) {
            return user;
        }

        user = new DiscordUser(member);
        manager.put(user);
        manager.save();

        return user;
    }

}
