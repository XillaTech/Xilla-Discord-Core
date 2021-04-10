package net.xilla.discord.manager.permission.user;

import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.manager.Manager;
import net.xilla.discord.api.command.Command;
import net.xilla.discord.api.permission.PermissionUser;
import net.xilla.discord.api.permission.UserProcessor;
import net.xilla.discord.manager.permission.group.DiscordGroup;

import java.util.ArrayList;
import java.util.List;

public class UserManager extends Manager<String, DiscordUser> implements UserProcessor {

    public UserManager() {
        super("DiscordUser", "users/guild-data.jsonf", DiscordUser.class);
    }

    @Override
    public void putObject(PermissionUser object) {
        put((DiscordUser) object);
    }

    @Override
    public void removeObject(PermissionUser object) {
        remove(object.getName());
    }

    @Override
    public PermissionUser create(User user) {
        return new DiscordUser();
    }

    @Override
    public List<PermissionUser> listObjects() {
        return new ArrayList<>(iterate());
    }

}
