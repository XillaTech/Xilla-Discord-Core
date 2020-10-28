package net.xilla.discordcore.core.permission.group;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.core.library.json.XillaJson;
import net.xilla.discordcore.core.command.permission.group.PermissionGroup;
import net.xilla.discordcore.core.manager.GuildManagerObject;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class DiscordGroup extends GuildManagerObject implements PermissionGroup {

    private String groupName;
    private List<String> permissions;

    public DiscordGroup(String groupID, String groupName, String guildID, ArrayList<String> permissions) {
        super(groupID, "Groups", guildID);
        this.groupName = groupName;
        this.permissions = permissions;
    }

    public DiscordGroup(JSONObject object) {
        super(object.get("groupID").toString(), "Groups", object.get("serverID").toString());
        loadSerializedData(new XillaJson(object));
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
        return getKey();
    }

    public String getGroupID() {
        return getKey();
    }

    public boolean isMember(Guild guild, String id) {
        ArrayList<String> roleIDs = new ArrayList<>();
        for(Role role : guild.getMemberById(id).getRoles())
            roleIDs.add(role.getId());
        return roleIDs.contains(getGroupID());
    }

    @Override
    public XillaJson getSerializedData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", groupName);
        map.put("groupID", getKey());
        map.put("serverID", getGroupID());
        map.put("permissions", permissions);

        return new XillaJson(new JSONObject(map));
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        JSONObject object = xillaJson.getJson();
        this.groupName = object.get("name").toString();
        setKey(object.get("groupID").toString());

        this.permissions = new Vector<>();
        for(Object obj : (List)object.get("permissions")) {
            permissions.add(obj.toString());
        }
    }
}
