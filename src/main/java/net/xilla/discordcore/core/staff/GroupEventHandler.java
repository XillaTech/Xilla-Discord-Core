package net.xilla.discordcore.core.staff;

import com.tobiassteely.tobiasapi.api.manager.ManagerEventExecutor;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

public class GroupEventHandler implements ManagerEventExecutor<Group> {

    @Override
    public Group loadObject(JSONObject jsonObject) {
        Group group = new Group(jsonObject);
        return group;
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
