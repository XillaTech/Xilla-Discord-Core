package net.xilla.discord.api.permission;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.NotNull;
import net.xilla.discord.api.Processor;
import net.xilla.discord.manager.permission.group.DiscordGroup;

/**
 * Processes and stores all discord groups
 */
public interface GroupProcessor extends Processor<String, PermissionGroup> {

    /**
     * Creates the group
     *
     * @param role Discord Role
     * @return Permission Group
     */
    public PermissionGroup create(Role role);

    /**
     * Safely pulls the role's data. If it does
     * not exist it will create them.
     * @param role Discord Role
     * @return Permission Group
     */
    @NotNull
    default PermissionGroup pull(Role role) {
        PermissionGroup permissionGroup = get(role.getId());
        if(permissionGroup != null) {
            return permissionGroup;
        }
        return create(role);
    }

}
