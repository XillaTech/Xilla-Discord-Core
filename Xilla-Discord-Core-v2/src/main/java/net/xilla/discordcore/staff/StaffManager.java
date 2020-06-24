package net.xilla.discordcore.staff;

import com.tobiassteely.tobiasapi.api.manager.ManagerCache;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.config.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.staff.department.DepartmentManager;
import net.xilla.discordcore.staff.group.Group;
import net.xilla.discordcore.staff.group.GroupManager;
import org.json.simple.JSONObject;

import java.util.ArrayList;

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
