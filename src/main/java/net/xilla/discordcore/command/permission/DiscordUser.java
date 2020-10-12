package net.xilla.discordcore.command.permission;

import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.command.permission.group.PermissionGroup;
import net.xilla.discordcore.core.command.permission.user.PermissionUser;
import net.xilla.discordcore.core.staff.Group;

import java.util.ArrayList;

public class DiscordUser extends CoreObject implements PermissionUser {

    @Getter
    private ArrayList<PermissionGroup> groups;

    private String identifier;

    @Getter
    private Member member;

    public DiscordUser(Member member) {
        this.groups = new ArrayList<>();

        Group defaultGroup = getGroupManager().getGroup(member.getGuild().getId() + "-default applies to all users");
        if(defaultGroup != null) {
            this.groups.add(defaultGroup);
        } else {
            Logger.log(LogLevel.WARN, "Could not find the default role!", getClass());
        }

        this.identifier = member.getId();
        this.member = member;

        for(Role role : member.getRoles()) {
            Group group = getGroupManager().get(role.getId());
            if(group != null) {
                groups.add(group);
            }
        }
    }


    @Override
    public PermissionGroup getPrimaryGroup() {
        return groups.get(0);
    }

    @Override
    public String getUserIdentifier() {
        return identifier;
    }

    @Override
    public boolean hasPermission(String permission) {

        if(DiscordCore.getInstance().getCoreSetting().isRespectDiscordAdmin()) {
            if(member.hasPermission(Permission.ADMINISTRATOR)) {
                return true;
            }
        }

        if(groups != null) {
            for (PermissionGroup group : new ArrayList<>(groups)) {
                if (group.hasPermission(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

}
