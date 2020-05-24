package net.xilla.discordcore.staff.department;

import com.tobiassteely.tobiasapi.api.config.Config;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.staff.group.Group;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DepartmentManager extends ManagerParent {

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
        for(ManagerObject object : getList()) {
            Department department = (Department) object;
            config.toJson().put(department.getKey(), department.toJson());
        }
        config.save();
    }

    public ArrayList<Department> getDepartmentByGroupName(String name) {
        ArrayList<Department> departmentList = new ArrayList<>();
        for(Object object : getList()) {
            Department department = (Department)object;

            if(department.getGroupNames().contains(name)) {
                departmentList.add(department);
            }
        }
        return departmentList;
    }

    public ArrayList<Department> getDepartmentByUserId(String id) {
        ArrayList<Department> departmentList = new ArrayList<>();
        for(Object object : getList()) {
            Department department = (Department)object;

            for(String groupName : department.getGroupNames()) {
                Group group = DiscordCore.getInstance().getPlatform().getStaffManager().getGroupManager().getStaff(groupName);
                if(group.isMember(id)) {
                    departmentList.add(department);
                }
                break;
            }
        }
        return departmentList;
    }

    public Department getDepartment(String name) {
        return (Department)getObjectWithKey(name);
    }

    public void registerDepartment(Department department) {
        addObject(department);
    }

}
