package net.xilla.discordcore.staff.group;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.config.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class GroupManager extends ManagerParent {

    public GroupManager() {
        super(true);
    }

    public void reload() {
        super.reload();

        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("staff/groups.json");
        JSONObject json = config.toJson();
        for(Object key : json.keySet()) {
            Group staff = new Group(config.getMap((String)key));
            registerStaff(staff);
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

    public ArrayList<Group> getStaffByUserId(Guild guild, String id) {
        ArrayList<Group> staffList = new ArrayList<>();
        for(Object object : getList()) {
            Group staff = (Group)object;
            if(staff.isMember(guild, id)) {
                staffList.add(staff);
            }
        }
        return staffList;
    }

    public boolean isAuthorized(Guild guild, String id, int level) {
        if(level == 0)
            return true;

        ArrayList<Group> staffList = getStaffByUserId(guild, id);
        for(Group staff : staffList)
            if(staff.getLevel() >= level)
                return true;
        return false;
    }

    public Group getStaff(String name) {
        return (Group)getObjectWithKey(name);
    }

    public void registerStaff(Group staff) {
        addObject(staff);
    }
    
}
