package net.xilla.discord.manager.permission.user;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.manager.Manager;
import net.xilla.discord.api.permission.PermissionUser;
import net.xilla.discord.api.permission.UserProcessor;

import java.util.ArrayList;
import java.util.List;

public class UserManager extends Manager<String, DiscordUser> implements UserProcessor {

    public UserManager() {
        super("DiscordUser", "permissions/users.jsonf", DiscordUser.class);
    }

    @Override
    public void putObject(PermissionUser object) {
        put((DiscordUser) object);
    }

    @Override
    public void removeObject(PermissionUser object) {
        remove(object.getId());
    }

    @Override
    public PermissionUser create(Member member) {
        DiscordUser discordUser = new DiscordUser(member);
        put(discordUser);
        return discordUser;
    }

    @Override
    public List<PermissionUser> listObjects() {
        return new ArrayList<>(iterate());
    }

}
