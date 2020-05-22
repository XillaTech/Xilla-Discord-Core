package net.xilla.discordcore.staff;

import net.xilla.discordcore.staff.department.DepartmentManager;
import net.xilla.discordcore.staff.group.GroupManager;

public class StaffManager {

    private GroupManager groupManager;
    private DepartmentManager departmentManager;

    public StaffManager() {
        this.groupManager = new GroupManager();
        this.departmentManager = new DepartmentManager();
    }

    public DepartmentManager getDepartmentManager() {
        return departmentManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

}
