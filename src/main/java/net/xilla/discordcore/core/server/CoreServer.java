package net.xilla.discordcore.core.server;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

public class CoreServer extends ManagerObject {

    private Guild guild;
    private int members;
    private long lastUpdated;

    public CoreServer(Guild guild) {
        super(guild.getId(), "Servers");
        this.members = guild.getMemberCount();
        this.lastUpdated = System.currentTimeMillis();
    }

    public CoreServer(JSONObject json) {
        super(json.get("id").toString(), "Servers");
        loadSerializedData(new XillaJson(json));
    }

    public void update(Guild guild) {
        this.members = guild.getMemberCount();
        this.lastUpdated = System.currentTimeMillis();
    }

    public int getMembers() {
        return members;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public Guild getGuild() {
        return guild;
    }

    @Override
    public XillaJson getSerializedData() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", this.getKey());
        jsonObject.put("members", this.getMembers());
        jsonObject.put("lastUpdated", this.getLastUpdated());

        return new XillaJson(jsonObject);
    }

    @Override
    public void loadSerializedData(XillaJson json) {
        this.members = Integer.parseInt(json.get("members").toString());
        this.lastUpdated = Long.parseLong(json.get("lastUpdated").toString());
    }
}
