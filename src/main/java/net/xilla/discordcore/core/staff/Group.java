package net.xilla.discordcore.core.staff;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.command.permission.group.PermissionGroup;
import com.tobiassteely.tobiasapi.config.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Group extends ManagerObject implements PermissionGroup {

    private String groupName;
    private String serverID;
    private String groupID;
    private List<String> permissions;

    public Group(String groupID, String groupName, String serverID, ArrayList<String> permissions) {
        super(serverID + "-" + groupID);
        this.groupID = groupID;
        this.groupName = groupName;
        this.serverID = serverID;
        this.permissions = permissions;
    }

    public Group(Map<String, Object> object) {
        super(object.get("serverID").toString() + "-" + object.get("groupID").toString());
        this.groupName = object.get("name").toString();
        this.groupID = object.get("groupID").toString();
        this.serverID = object.get("serverID").toString();

        this.permissions = new Vector<>();
        for(Object obj : (JSONArray)object.get("permissions")) {
            permissions.add(obj.toString());
        }
    }

    public String getName() {
        return groupName;
    }

    @Override
    public List<String> getPermissions() {
        return permissions;
    }

    public void addPermission(String permission) {
        permissions.add(permission);
    }

    public void removePermission(String permission) {
        permissions.remove(permission);
    }

    @Override
    public boolean hasPermission(String permission) {
        for(String groupPerm : permissions) {

            String[] temp = permission.split("\\.");
            StringBuilder wildcard = new StringBuilder();
            for(int i = 0; i <= temp.length - 2; i++) {
                wildcard.append(temp[0]);
            }
            wildcard.append(".*");

            if(groupPerm.equalsIgnoreCase(permission)) {
                return true;
            } else if(groupPerm.equalsIgnoreCase(wildcard.toString())) {
                return true;
            } else if(groupPerm.equalsIgnoreCase("*")) {
                return true;
            }
        }
        return false;
    }
    public String getServerID() {
        return serverID;
    }

    @Override
    public String getIdentifier() {
        return getKey();
    }

    public String getGroupID() {
        return groupID;
    }

    public boolean isMember(Guild guild, String id) {
        ArrayList<String> roleIDs = new ArrayList<>();
        for(Role role : guild.getMemberById(id).getRoles())
            roleIDs.add(role.getId());
        return roleIDs.contains(getGroupID());
    }

    public JSONObject toJson() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", groupName);
        map.put("groupID", groupID);
        map.put("serverID", serverID);
        map.put("permissions", permissions);
        return new JSONObject(map);
    }
}
