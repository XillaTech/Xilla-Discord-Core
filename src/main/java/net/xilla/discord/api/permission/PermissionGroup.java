package net.xilla.discord.api.permission;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.Nullable;

/**
 * Base permission group, used to group permissions
 * together for permission users.
 */
public interface PermissionGroup extends PermissionEntity {

    /**
     * Returns the groups public name
     *
     * @return Group Id
     */
    String getId();

    /**
     * Returns the raw discord role (if available)
     *
     * @return Discord Role
     */
    @Nullable
    Role getRole();

}
