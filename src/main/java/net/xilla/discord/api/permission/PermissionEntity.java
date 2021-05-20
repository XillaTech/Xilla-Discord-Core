package net.xilla.discord.api.permission;

import net.xilla.core.library.manager.ObjectInterface;

import java.util.Arrays;
import java.util.List;

/**
 * Base permission entity to check permissions
 */
public interface PermissionEntity extends ObjectInterface {

    /**
     * Checks if the permission user has the appropriate
     * permissions for the input permission.
     *
     * @param neededPerm Required Permission
     * @return Has Permission
     */
    static boolean hasPermission(List<String> permissions, String neededPerm) {

        // Generating needed data
        String[] sections = neededPerm.split("\\.");
        String[] updatedSections = Arrays.copyOfRange(sections, 0, sections.length - 1);
        String shortenedPermission = String.join(".", updatedSections);

        // Checks for normal permissions first
        for(String perm : permissions) {
            // Checking for full wildcard permissions
            if(perm.equalsIgnoreCase("*")) return true;

            // Checking for partial wildcard permissions
            if(perm.equalsIgnoreCase(shortenedPermission + ".*")) return true;

            // Checking if they contain the current permission
            if(perm.equalsIgnoreCase(neededPerm)) return true;
        }

        // Checking if they have any wildcard permissions
        if(sections.length > 1) {
            boolean hasPerm = PermissionEntity.hasPermission(permissions, shortenedPermission);
            if(hasPerm) return true;
        }

        return false;
    }

    default boolean hasPermission(String permission) {
        return PermissionEntity.hasPermission(getPermissions(), permission);
    }

    /**
     * Returns the users full list of permissions
     *
     * @return Users Permissions
     */
    List<String> getPermissions();

}
