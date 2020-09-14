package net.xilla.discordcore.core.staff;

import com.tobiassteely.tobiasapi.api.manager.ManagerEventExecutor;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class GroupEventHandler implements ManagerEventExecutor<Group> {

    @Override
    public Group loadObject(JSONObject jsonObject) {
        return new Group(jsonObject);
    }

    @Override
    public JSONObject saveObject(Group group) {
        return group.toJson();
    }

    @Override
    public void onObjectAdd(ManagerParent<Group> managerParent, Group group) {
        managerParent.getCache("groupID").putObject(group.getGroupID(), group);
        DiscordCore.getInstance().getGroupManager().addGroup(group);
    }

    @Override
    public void onObjectRemove(ManagerParent<Group> managerParent, String s, Group group) {

    }

    @Override
    public void onReload(ManagerParent<Group> managerParent) {
        for(Guild guild : DiscordCore.getInstance().getBot().getGuilds()) {
            Group defaultGroup = DiscordCore.getInstance().getGroupManager().getGroup(guild.getId() + "-default applies to all users");
            if(defaultGroup == null) {
                Group group = new Group("default applies to all users", "Default", guild.getId(), new ArrayList<>());
                managerParent.addObject(group);
            }
        }
        managerParent.save();
    }

    @Override
    public void onLoad(ManagerParent<Group> managerParent) {

    }

    @Override
    public void onUnload(ManagerParent<Group> managerParent) {

    }

    @Override
    public void onSave(ManagerParent<Group> managerParent, JSONObject json) {

    }
}
