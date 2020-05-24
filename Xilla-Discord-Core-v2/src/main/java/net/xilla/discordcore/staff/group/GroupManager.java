package net.xilla.discordcore.staff.group;

import com.tobiassteely.tobiasapi.api.config.Config;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class GroupManager extends ManagerParent {

    public void reload() {
        super.reload();

        Config config = DiscordCore.getInstance().getApi().getConfigManager().getConfig("staff/groups.json");
        JSONObject json = config.toJson();
        for(Object key : json.keySet()) {
            Group staff = new Group(config.getMap((String)key));
            registerStaff(staff);
        }
    }

    public void save() {
        Config config = DiscordCore.getInstance().getApi().getConfigManager().getConfig("staff/groups.json");
        for(ManagerObject object : getList()) {
            Group staff = (Group)object;
            config.toJson().put(staff.getKey(), staff.toJson());
        }
        config.save();
    }

    public ArrayList<Group> getStaffByUserId(String id) {
        ArrayList<Group> staffList = new ArrayList<>();
        for(Object object : getList()) {
            Group staff = (Group)object;
            if(staff.isMember(id)) {
                staffList.add(staff);
            }
        }
        return staffList;
    }

    public boolean isAuthorized(String id, int level) {
        if(level == 0)
            return true;

        ArrayList<Group> staffList = getStaffByUserId(id);
        for(Group staff : staffList)
            if(staff.getLevel() >= level)
                return true;
        return false;
    }

    public Group getStaff(String name) {
        return (Group)getObjectWithKey(name);
    }

    public void registerStaff(Group staff) {
        super.addObject(staff);
    }
    
}
