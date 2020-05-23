package net.xilla.discordcore.staff.group;

import com.tobiassteely.tobiasapi.api.config.Config;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.discordcore.DiscordCore;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Group extends ManagerObject {

    private int staffLevel;
    private String groupID;
    private ArrayList<String> userIds;

    public Group(String groupName, int staffLevel, String groupID, String... userIDs) {
        super(groupName);
        this.staffLevel = staffLevel;
        this.groupID = groupID;
        if(userIDs != null) {
            this.userIds = new ArrayList<>(userIds);
        } else {
            this.userIds = new ArrayList<>();
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

    public boolean isMember(String id) {
        Config config = DiscordCore.getInstance().getApi().getConfigManager().getConfig("settings.json");
        ArrayList<String> roleIDs = new ArrayList<>();
        for(Role role : Objects.requireNonNull(Objects.requireNonNull(DiscordCore.getInstance().getBot().getGuildById(config.getString("guildID"))).getMemberById(id)).getRoles())
            roleIDs.add(role.getId());
        return roleIDs.contains(groupID);
    }

    public org.json.simple.JSONObject toJson() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", getKey());
        map.put("level", "" + staffLevel);
        map.put("groupID", groupID);
        return new JSONObject(map);
    }
}
