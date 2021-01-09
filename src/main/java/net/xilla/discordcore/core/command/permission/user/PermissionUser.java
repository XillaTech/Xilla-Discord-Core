package net.xilla.discordcore.core.command.permission.user;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.core.command.permission.group.PermissionGroup;

import java.util.List;

public interface PermissionUser {

    List<PermissionGroup> getGroups();

    boolean hasPermission(Guild guild, String permission);

    PermissionGroup getPrimaryGroup();

    String getUserIdentifier();

}
