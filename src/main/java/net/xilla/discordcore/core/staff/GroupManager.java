package net.xilla.discordcore.core.staff;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GroupManager extends ManagerParent<Group> {

    public GroupManager() {
        super("XDC.Group", false, "groups.json", new GroupEventHandler());

        this.groupCache = new ConcurrentHashMap<>();
        this.serverCache = new ConcurrentHashMap<>();

        DiscordCore.getInstance().addExecutor(this::reload);
    }
    private ConcurrentHashMap<String, List<Group>> groupCache;
    private ConcurrentHashMap<String, List<Group>> serverCache;

    public List<Group> getGroupsByServer(String id) {
        return serverCache.get(id);
    }

    public ArrayList<Group> getStaffByUserId(Guild guild, String id) {
        ArrayList<Group> staffList = new ArrayList<>();
        for(Group staff : getList()) {
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
        return getObject(id);
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
    
}
