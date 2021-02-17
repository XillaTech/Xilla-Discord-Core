package net.xilla.discordcore.core.permission.group;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.core.library.manager.StoredData;
import net.xilla.discordcore.core.command.permission.group.PermissionGroup;
import net.xilla.discordcore.core.manager.GuildManagerObject;

import java.util.ArrayList;
import java.util.List;

public class DiscordGroup extends GuildManagerObject implements PermissionGroup {

    @Getter
    @StoredData
    private String groupName;

    @Getter
    @StoredData
    private List<String> permissions;

    public DiscordGroup(String groupID, String groupName, String guildID, ArrayList<String> permissions) {
        super(groupID, "Groups", guildID);
        this.groupName = groupName;
        this.permissions = permissions;
    }

    public DiscordGroup() {
    }

    public void addPermission(String permission) {
        permissions.add(permission);
    }

    public void removePermission(String permission) {
        permissions.remove(permission);
    }

    @Override
    public boolean hasPermission(String permission) {
        String[] temp = permission.split("\\.");
        StringBuilder wildcard = new StringBuilder();
        for(int i = 0; i <= temp.length - 2; i++) {
            wildcard.append(temp[0]);
        }
        wildcard.append(".*");

        for(String groupPerm : permissions) {
            if(groupPerm.equalsIgnoreCase(permission)) {
                return true;
            } else if(groupPerm.equalsIgnoreCase(wildcard.toString())) {
                return true;
            } else if(!permission.toLowerCase().startsWith("core.") && groupPerm.equalsIgnoreCase("*")) {
                return true;
            }
        }
        return false;
    }
    public String getServerID() {
        return getGuildID();
    }

    @Override
    public String getIdentifier() {
        return getKey().toString();
    }

    public String getGroupID() {
        return getKey().toString();
    }

    public boolean isMember(Guild guild, String id) {
        ArrayList<String> roleIDs = new ArrayList<>();
        for(Role role : guild.getMemberById(id).getRoles())
            roleIDs.add(role.getId());
        return roleIDs.contains(getGroupID());
    }

}
