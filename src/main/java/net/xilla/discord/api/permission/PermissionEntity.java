package net.xilla.discord.api.permission;

import java.util.List;

/**
 * Base permission entity to check permissions
 */
public interface PermissionEntity {

    /**
     * Checks if the permission user has the appropriate
     * permissions for the input permission.
     *
     * @param permission Required Permission
     * @return Has Permission
     */
    boolean hasPermission(String permission);

    /**
     * Returns the users full list of permissions
     *
     * @return Users Permissions
     */
    List<String> getPermissions();

}
