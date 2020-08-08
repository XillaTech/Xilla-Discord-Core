package net.xilla.discordcore.core.staff;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.command.permission.group.PermissionGroup;
import com.tobiassteely.tobiasapi.config.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group extends ManagerObject implements PermissionGroup {

    private String groupID;
    private String serverID;
    private List<String> permissions;

    public Group(String groupName, String groupID, String serverID, ArrayList<String> permissions) {
        super(groupName);
        this.groupID = groupID;
        this.serverID = serverID;
        this.permissions = permissions;
    }

    public Group(Map<String, Object> object) {
        super(object.get("name").toString());
        this.groupID = object.get("groupID").toString();
        if(object.containsKey("serverID")) {
            this.serverID = object.get(serverID).toString();
        } else {
            this.serverID = null;
        }
        this.groupID = object.get("groupID").toString();
        this.permissions = (List<String>)object.get("permissions");
    }

    public String getName() {
        return getKey();
    }

    @Override
    public List<String> getPermissions() {
        return permissions;
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

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public boolean isMember(Guild guild, String id) {
        ArrayList<String> roleIDs = new ArrayList<>();
        for(Role role : guild.getMemberById(id).getRoles())
            roleIDs.add(role.getId());
        return roleIDs.contains(groupID);
    }

    public JSONObject toJson() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", getKey());
        map.put("groupID", groupID);
        map.put("serverID", serverID);
        map.put("permissions", permissions);
        return new JSONObject(map);
    }
}
