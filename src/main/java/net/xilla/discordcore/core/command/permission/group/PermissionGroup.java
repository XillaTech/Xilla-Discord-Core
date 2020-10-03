package net.xilla.discordcore.core.command.permission.group;

import java.util.List;

public interface PermissionGroup {

    List<String> getPermissions();

    boolean hasPermission(String permission);

    String getIdentifier();

}
