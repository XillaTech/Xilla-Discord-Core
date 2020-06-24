package net.xilla.discordcore.staff.group;

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

    private int staffLevel;
    private String groupID;
    private List<String> permissions;

    public Group(String groupName, int staffLevel, String groupID, ArrayList<String> permissions) {
        super(groupName);
        this.staffLevel = staffLevel;
        this.groupID = groupID;
        this.permissions = permissions;
    }

    public Group(Map<String, Object> object) {
        super(object.get("name").toString());
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

    public int getLevel() {
        return staffLevel;
    }

    @Override
    public String getIdentifier() {
        return getKey();
    }

    public String getGroupID() {
        return groupID;
    }

    public void setLevel(int level) {
        this.staffLevel = level;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public boolean isMember(Guild guild, String id) {
        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("settings.json");
        ArrayList<String> roleIDs = new ArrayList<>();
        for(Role role : guild.getMemberById(id).getRoles())
            roleIDs.add(role.getId());
        return roleIDs.contains(groupID);
    }

    public JSONObject toJson() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", getKey());
        map.put("level", "" + staffLevel);
        map.put("groupID", groupID);
        map.put("permissions", permissions);
        return new JSONObject(map);
    }
}
