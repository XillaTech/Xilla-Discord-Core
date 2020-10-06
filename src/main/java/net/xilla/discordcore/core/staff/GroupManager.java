package net.xilla.discordcore.core.staff;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GroupManager extends Manager<Group> {

    private Map<String, List<Group>> groupCache = new ConcurrentHashMap<>();
    private Map<String, List<Group>> serverCache = new ConcurrentHashMap<>();

    public GroupManager() {
        super("Groups", "groups.json");

        DiscordCore.getInstance().addExecutor(() -> {
            for(Guild guild : DiscordCore.getInstance().getBot().getGuilds()) {
                Group defaultGroup = DiscordCore.getInstance().getGroupManager().getGroup(guild.getId() + "-default applies to all users");
                if(defaultGroup == null) {
                    Group group = new Group("default applies to all users", "Default", guild.getId(), new ArrayList<>());
                    put(group);
                }
            }
            save();
        });
    }

    public List<Group> getGroupsByServer(String id) {
        return serverCache.get(id);
    }

    public ArrayList<Group> getStaffByUserId(Guild guild, String id) {
        ArrayList<Group> staffList = new ArrayList<>();
        for(Group staff : new ArrayList<>(getData().values())) {
            if(staff.isMember(guild, id)) {
                staffList.add(staff);
            }
        }
        return staffList;
    }

    public List<Group> getGroupsByName(String name) {
        return groupCache.get(name);
    }

    public Group getGroup(String id) {
        return get(id);
    }

    public void addGroup(Group staff) {
        if (!groupCache.containsKey(staff.getName())) {
            groupCache.put(staff.getName(), new Vector<>());
        }
        groupCache.get(staff.getName()).add(staff);

        if (!serverCache.containsKey(staff.getServerID())) {
            serverCache.put(staff.getServerID(), new Vector<>());
        }
        serverCache.get(staff.getServerID()).add(staff);
    }

    @Override
    public void load() {
        for(Object key : getConfig().getJson().getJson().keySet()) {
            JSONObject data = getConfig().getJson().get(key.toString());
            put(new Group(data));
        }
    }

    @Override
    public void objectAdded(Group group) {
        getCache("groupID").putObject(group.getGroupID(), group);
        DiscordCore.getInstance().getGroupManager().addGroup(group);
    }

    @Override
    public void objectRemoved(Group group) {

    }
}
