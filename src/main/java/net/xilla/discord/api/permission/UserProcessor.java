package net.xilla.discord.api.permission;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.NotNull;
import net.xilla.discord.api.Processor;

/**
 * Processes and stores all discord users
 */
public interface UserProcessor extends Processor<String, PermissionUser> {

    /**
     * Creates the user
     *
     * @param member Discord User
     * @return Permission User
     */
    @NotNull
    PermissionUser create(Member member);

    /**
     * Safely pulls the user's data. If they do
     * not exist it will create them.
     * @param member Discord Member
     * @return Permission User
     */
    @NotNull
    default PermissionUser pull(Member member) {
        PermissionUser permissionUser = get(member.getId());
        if(permissionUser != null) {
            return permissionUser;
        }
        return create(member);
    }

}
