package net.xilla.discord.api.permission;

/**
 * Base permission group, used to group permissions
 * together for permission users.
 */
public interface PermissionGroup extends PermissionEntity {

    /**
     * Returns the groups public name
     *
     * @return Group Name
     */
    String getName();

}
