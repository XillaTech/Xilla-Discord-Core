package net.xilla.discordcore.core.server;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

public class ServerManager extends ManagerParent<CoreServer> {

    public ServerManager() {
        super("XDC.Server", false, "servers.json");
        DiscordCore.getInstance().addExecutor(this::reload);
    }

    public void addServer(CoreServer server) {
        if(getCache("key").isCached(server.getKey())) {
            removeObject(server.getKey());
        }
        addObject(server);
    }

    @Override
    public JSONObject saveObject(CoreServer object) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", object.getKey());
        jsonObject.put("members", object.getMembers());
        jsonObject.put("lastUpdated", object.getLastUpdated());
        return jsonObject;
    }

    @Override
    public boolean loadObject(JSONObject json) {

        String id = json.get("id").toString();
        int members = Integer.parseInt(json.get("members").toString());
        long lastUpdated = Long.parseLong(json.get("lastUpdated").toString());

        addServer(new CoreServer(id, members, lastUpdated));

        return true;
    }


}
