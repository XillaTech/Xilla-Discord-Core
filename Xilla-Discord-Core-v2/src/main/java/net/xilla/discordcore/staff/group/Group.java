package net.xilla.discordcore.staff.group;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.config.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Group extends ManagerObject {

    private int staffLevel;
    private String groupID;
    private ArrayList<String> userIDs;

    public Group(String groupName, int staffLevel, String groupID, ArrayList<String> userIDs) {
        super(groupName);
        this.staffLevel = staffLevel;
        this.groupID = groupID;
        if(userIDs != null) {
            this.userIDs = new ArrayList<>(userIDs);
        } else {
            this.userIDs = new ArrayList<>();
        }
    }

    public Group(Map<String, String> map) {
        super(map.get("name"));
        this.staffLevel = Integer.parseInt(map.get("level"));
        this.groupID = map.get("groupID");
    }

    public String getName() {
        return getKey();
    }

    public int getLevel() {
        return staffLevel;
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
        HashMap<String, String> map = new HashMap<>();
        map.put("name", getKey());
        map.put("level", "" + staffLevel);
        map.put("groupID", groupID);
        return new JSONObject(map);
    }
}
