package net.xilla.discordcore.core.staff;

import com.tobiassteely.tobiasapi.api.manager.ManagerEventExecutor;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import org.json.simple.JSONObject;

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
        if(group.getGroupID() != null) {
            managerParent.getCache("groupID").putObject(group.getGroupID(), group);
        }
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
