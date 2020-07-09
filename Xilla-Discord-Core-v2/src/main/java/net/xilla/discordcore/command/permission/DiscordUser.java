package net.xilla.discordcore.command.permission;

import com.tobiassteely.tobiasapi.command.permission.group.PermissionGroup;
import com.tobiassteely.tobiasapi.command.permission.user.PermissionUser;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.staff.group.Group;

import java.util.ArrayList;

public class DiscordUser extends CoreObject implements PermissionUser {

    private ArrayList<PermissionGroup> groups;
    private String identifier;

    public DiscordUser(Member member) {
        this.groups = new ArrayList<>();
        this.groups.add(getStaffManager().getGroupManager().getGroup("Default"));
        this.identifier = member.getId();

        for(Role role : member.getRoles()) {
            Group group = getStaffManager().getGroupManager().getGroupByID(role.getId());
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
        for(PermissionGroup group : groups) {
            if(group.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

}
