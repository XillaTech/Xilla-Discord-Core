package net.xilla.discordcore.core.command.handler;

import net.xilla.discordcore.core.command.permission.group.PermissionGroup;
import net.xilla.discordcore.core.command.permission.user.PermissionUser;

import java.util.List;

public class ConsoleUser implements PermissionUser {

    @Override
    public List<PermissionGroup> getGroups() {
        return null;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public PermissionGroup getPrimaryGroup() {
        return null;
    }

    @Override
    public String getUserIdentifier() {
        return "Console";
    }

}
