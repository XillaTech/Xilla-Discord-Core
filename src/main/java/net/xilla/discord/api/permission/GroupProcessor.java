package net.xilla.discord.api.permission;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.xilla.discord.api.Processor;
import net.xilla.discord.manager.permission.group.DiscordGroup;

/**
 * Processes and stores all discord groups
 */
public interface GroupProcessor extends Processor<PermissionGroup> {

    public PermissionGroup create(Role role);

}
