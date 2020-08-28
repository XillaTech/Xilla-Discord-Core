package net.xilla.discordcore.core.server;

import com.tobiassteely.tobiasapi.api.manager.ManagerEventExecutor;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.CoreObject;
import org.json.simple.JSONObject;

public class ServerEventHandler extends CoreObject implements ManagerEventExecutor<CoreServer> {

    @Override
    public void onObjectAdd(ManagerParent<CoreServer> manager, CoreServer server) {
    }

    @Override
    public void onObjectRemove(ManagerParent<CoreServer> managerParent, String s, CoreServer server) {
    }

    @Override
    public void onReload(ManagerParent<CoreServer> manager) {
        for(Guild guild : getBot().getGuilds()) {
            if(manager.contains(guild.getId())) {
                CoreServer obj = manager.getObject(guild.getId());
                obj.update(guild);
            } else {
                manager.addObject(new CoreServer(guild));
            }
        }
    }

    @Override
    public void onLoad(ManagerParent<CoreServer> managerParent) {

    }

    @Override
    public void onUnload(ManagerParent<CoreServer> managerParent) {

    }

    @Override
    public void onSave(ManagerParent<CoreServer> managerParent) {

    }

    @Override
    public JSONObject saveObject(CoreServer server) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", server.getKey());
        jsonObject.put("members", server.getMembers());
        jsonObject.put("lastUpdated", server.getLastUpdated());
        return jsonObject;
    }

    @Override
    public CoreServer loadObject(JSONObject json) {
        String id = json.get("id").toString();
        int members = Integer.parseInt(json.get("members").toString());
        long lastUpdated = Long.parseLong(json.get("lastUpdated").toString());

        return new CoreServer(id, members, lastUpdated);
    }

}
