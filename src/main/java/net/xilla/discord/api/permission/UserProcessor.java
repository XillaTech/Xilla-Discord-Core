package net.xilla.discord.api.permission;

import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.NotNull;
import net.xilla.discord.api.Processor;

/**
 * Processes and stores all discord users
 */
public interface UserProcessor extends Processor<PermissionUser> {

    /**
     * Creates the user
     *
     * @param user Discord User
     * @return Permission User
     */
    @NotNull
    PermissionUser create(User user);

    /**
     * Safely pulls the users data. If they do
     * not exist it will create them.
     * @param user
     * @return
     */
    @NotNull
    default PermissionUser pull(User user) {
        PermissionUser permissionUser = get(user.getId());
        if(permissionUser != null) {
            return permissionUser;
        }
        return create(user);
    }

}
