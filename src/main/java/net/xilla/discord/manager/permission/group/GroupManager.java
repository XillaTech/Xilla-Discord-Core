package net.xilla.discord.manager.permission.group;

import net.dv8tion.jda.api.entities.Role;
import net.xilla.core.library.manager.Manager;
import net.xilla.discord.api.command.Command;
import net.xilla.discord.api.permission.GroupProcessor;
import net.xilla.discord.api.permission.PermissionGroup;
import net.xilla.discord.api.permission.PermissionUser;

import java.util.ArrayList;
import java.util.List;

public class GroupManager extends Manager<String, DiscordGroup> implements GroupProcessor {

    public GroupManager() {
        super("DiscordGroup", "groups/guild-data.jsonf", DiscordGroup.class);
    }

    @Override
    public void putObject(PermissionGroup object) {
        putObject(object);
    }

    @Override
    public void removeObject(PermissionGroup object) {
        remove(object.getName());
    }

    @Override
    public PermissionGroup create(Role role) {
        return new DiscordGroup();
    }

    @Override
    public List<PermissionGroup> listObjects() {
        return new ArrayList<>(iterate());
    }

}
