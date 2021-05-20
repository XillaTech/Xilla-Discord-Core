package net.xilla.discord.manager.permission.group;

import net.dv8tion.jda.api.entities.Role;
import net.xilla.core.library.manager.Manager;
import net.xilla.discord.api.permission.GroupProcessor;
import net.xilla.discord.api.permission.PermissionGroup;

import java.util.ArrayList;
import java.util.List;

public class GroupManager extends Manager<String, DiscordGroup> implements GroupProcessor {

    public GroupManager() {
        super("DiscordGroup", "permissions/roles.jsonf", DiscordGroup.class);
    }

    @Override
    public void putObject(PermissionGroup object) {
        putObject(object);
    }

    @Override
    public void removeObject(PermissionGroup object) {
        remove(object.getId());
    }

    @Override
    public PermissionGroup create(Role role) {
        DiscordGroup group = new DiscordGroup(role);
        put(group);
        return group;
    }

    @Override
    public List<PermissionGroup> listObjects() {
        return new ArrayList<>(iterate());
    }

}
