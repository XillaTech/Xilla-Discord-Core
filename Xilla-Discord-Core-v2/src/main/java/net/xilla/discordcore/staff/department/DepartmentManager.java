package net.xilla.discordcore.staff.department;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.config.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.staff.group.Group;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DepartmentManager extends ManagerParent<Department> {

    public DepartmentManager() {
        super(true);
    }

    public void reload() {
        super.reload();

        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("staff/departments.json");
        JSONObject json = config.toJson();
        for(Object key : json.keySet()) {
            Department department = new Department(config.getMap((String)key));
            registerDepartment(department);
        }
    }

    public void save() {
        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("staff/departments.json");
        for(Department department : getList()) {
            config.toJson().put(department.getKey(), department.toJson());
        }
        config.save();
    }

    public ArrayList<Department> getDepartmentByGroupName(String name) {
        ArrayList<Department> departmentList = new ArrayList<>();
        for(Department department : getList()) {

            if(department.getGroupNames().contains(name)) {
                departmentList.add(department);
            }
        }
        return departmentList;
    }

    public ArrayList<Department> getDepartmentByUserId(Guild guild, String id) {
        ArrayList<Department> departmentList = new ArrayList<>();
        for(Department department : getList()) {

            for(String groupName : department.getGroupNames()) {
                Group group = DiscordCore.getInstance().getPlatform().getStaffManager().getGroupManager().getGroup(groupName);
                if(group.isMember(guild, id)) {
                    departmentList.add(department);
                }
                break;
            }
        }
        return departmentList;
    }

    public Department getDepartment(String name) {
        return getObject(name);
    }

    public void registerDepartment(Department department) {
        addObject(department);
    }

}
