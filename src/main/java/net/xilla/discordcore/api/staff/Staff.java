package net.xilla.discordcore.api.staff;

import net.dv8tion.jda.api.entities.Role;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.api.config.ConfigManager;
import net.xilla.discordcore.api.manager.ManagerObject;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Staff extends ManagerObject {

    private String name;
    private int level;
    private String groupID;

    public Staff(String name, int level, String groupID) {
        super(name, ConfigManager.getInstance().getConfig("staff.json"));
        this.name = name;
        this.level = level;
        this.groupID = groupID;
    }

    public Staff(Map<String, String> map) {
        super(map.get("name"), ConfigManager.getInstance().getConfig("staff.json"));
        this.name = map.get("name");
        this.level = Integer.parseInt(map.get("level"));
        this.groupID = map.get("groupID");
    }

    @Override
    public org.json.simple.JSONObject toJson() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("level", "" + level);
        map.put("groupID", groupID);
        return new JSONObject(map);
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public boolean isMember(String id) {
        Config config = ConfigManager.getInstance().getConfig("settings.json");
        ArrayList<String> roleIDs = new ArrayList<>();
        for(Role role : Objects.requireNonNull(Objects.requireNonNull(DiscordCore.getInstance().getBot().getGuildById(config.getString("guildID"))).getMemberById(id)).getRoles())
            roleIDs.add(role.getId());
        return roleIDs.contains(groupID);
    }
}
