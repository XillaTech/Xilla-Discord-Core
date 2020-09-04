package net.xilla.discordcore.core.staff;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.dv8tion.jda.api.entities.Guild;
import org.json.simple.JSONObject;

import java.util.*;

public class GroupManager extends ManagerParent<Group> {

    public GroupManager() {
        super("XDC.Group", true, "groups.json", new GroupEventHandler());
    }
    private HashMap<String, List<Group>> groupCache;

    @Override
    public void reload() {
        super.reload();

        this.groupCache = new HashMap<>();

        JSONObject json = getData().toJson();
        for(Object key : json.keySet()) {
            Group staff = new Group((Map<String, Object>)getData().get(key.toString()));
            addGroup(staff);
        }
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
        super.addObject(staff);

        if (!groupCache.containsKey(staff.getName())) {
            groupCache.put(staff.getName(), new Vector<>());
        }
        groupCache.get(staff.getName()).add(staff);
    }
    
}
