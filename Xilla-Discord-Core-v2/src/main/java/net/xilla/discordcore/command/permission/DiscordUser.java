package net.xilla.discordcore.command.permission;

import com.tobiassteely.tobiasapi.command.permission.group.PermissionGroup;
import com.tobiassteely.tobiasapi.command.permission.user.PermissionUser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.staff.Group;

import java.util.ArrayList;

public class DiscordUser extends CoreObject implements PermissionUser {

    private ArrayList<PermissionGroup> groups;
    private String identifier;
    private Member member;

    public DiscordUser(Member member) {
        this.groups = new ArrayList<>();
        this.groups.add(getGroupManager().getGroup("Default"));
        this.identifier = member.getId();
        this.member = member;

        for(Role role : member.getRoles()) {
            Group group = getGroupManager().getGroupByID(role.getId());
            if(group != null) {
                groups.add(group);
            }
        }
    }

    @Override
    public ArrayList<PermissionGroup> getGroups() {
        return groups;
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

        for(PermissionGroup group : groups) {
            if(group.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

}
