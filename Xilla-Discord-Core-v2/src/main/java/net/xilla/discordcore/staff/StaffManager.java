package net.xilla.discordcore.staff;

import com.tobiassteely.tobiasapi.api.Log;
import com.tobiassteely.tobiasapi.api.config.Config;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.dv8tion.jda.api.entities.User;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.staff.department.DepartmentManager;
import net.xilla.discordcore.staff.group.Group;
import net.xilla.discordcore.staff.group.GroupManager;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class StaffManager extends ManagerParent {

    private GroupManager groupManager;
    private DepartmentManager departmentManager;

    public StaffManager() {
        super(true);
        this.groupManager = new GroupManager();
        this.departmentManager = new DepartmentManager();
    }

    public DepartmentManager getDepartmentManager() {
        return departmentManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void reload() {
        super.reload();

        Log.sendMessage(0, DiscordCore.getInstance().toString());
        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("staff/groups.json");
        JSONObject json = config.toJson();
        for(Object key : json.keySet()) {
            Group staff = new Group(config.getMap((String)key));
            addGroup(staff);
        }
    }

    public void save() {
        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("staff/groups.json");
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

    public boolean hasPermission(User user, int level) {
        if(level == 0)
            return true;

        ArrayList<Group> staffList = getStaffByUserId(user.getId());
        for(Group staff : staffList)
            if(staff.getLevel() >= level)
                return true;
        return false;
    }

    public Group getGroup(String name) {
        return (Group)getObjectWithKey(name);
    }

    public void addGroup(Group staff) {
        super.addObject(staff);
    }

}
