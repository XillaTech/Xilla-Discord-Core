package net.xilla.discord.api.permission;

import java.util.List;

/**
 * Base permission user, used to store their related groups
 * and permissions.
 */
public interface PermissionUser extends PermissionEntity {

    /**
     * Returns the users public name
     *
     * @return User Name
     */
    String getName();

    /**
     * Returns the users current groups
     *
     * @return Groups
     */
    List<PermissionGroup> getGroups();

}
