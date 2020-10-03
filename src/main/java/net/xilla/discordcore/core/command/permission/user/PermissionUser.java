package net.xilla.discordcore.core.command.permission.user;

import net.xilla.discordcore.core.command.permission.group.PermissionGroup;

import java.util.List;

public interface PermissionUser {

    List<PermissionGroup> getGroups();

    boolean hasPermission(String permission);

    PermissionGroup getPrimaryGroup();

    String getUserIdentifier();

}
