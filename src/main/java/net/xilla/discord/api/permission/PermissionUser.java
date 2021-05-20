package net.xilla.discord.api.permission;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.NotNull;
import net.xilla.core.library.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Base permission user, used to store their related groups
 * and permissions.
 */
public interface PermissionUser extends PermissionEntity {

    /**
     * Returns the users public name
     *
     * @return User Id
     */
    @NotNull
    String getId();

    /**
     * Returns the users current groups
     *
     * @return Groups
     */
    @NotNull
    List<PermissionGroup> getGroups();

    /**
     * Returns the raw discord user (if available)
     *
     * @return Discord Member
     */
    @Nullable
    Member getMember();


    /**
     * Returns whether the user has a required permission or not.
     *
     * @param neededPerm Needed Permission
     * @return Boolean Value
     */
    @Override
    boolean hasPermission(String neededPerm);

}
