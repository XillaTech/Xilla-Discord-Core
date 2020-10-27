package net.xilla.community.reaction;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.core.manager.GuildManagerObject;

public class ReactionRole extends GuildManagerObject {

    private String name;
    private int cost;

    public ReactionRole(String roleID, String name, int cost, Guild guild) {
        super(roleID, "Reactions", guild);
        this.name = name;
        this.cost = cost;
    }

    public ReactionRole() {
        super("", "Reactions", "");
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("name", name);
        json.put("cost", cost);
        json.put("roleID", getKey());
        json.put("serverID", getGuildID());

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        setKey(xillaJson.get("roleID"));
        setGuildID(xillaJson.get("serverID"));

        this.name = xillaJson.get("name");
        this.cost = Integer.parseInt(xillaJson.get("cost").toString());
    }
}
