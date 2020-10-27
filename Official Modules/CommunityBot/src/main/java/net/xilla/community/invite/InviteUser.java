package net.xilla.community.invite;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.core.manager.GuildManagerObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InviteUser extends GuildManagerObject {

    private List<Invite> invites = new ArrayList<>();

    public InviteUser(String userID, Guild guild) {
        super(userID, "Invites", guild);
    }

    public InviteUser() {
        super("", "Invites", "");
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("serverID", getGuildID());
        json.put("userID", getGuildID());

        JSONArray array = new JSONArray();
        for(Invite invite : new ArrayList<>(invites)) {
            array.add(invite.getSerializedData());
        }
        json.put("invites", array);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        setGuildID(xillaJson.get("serverID"));
        setKey(xillaJson.get("userID"));

        JSONArray array = xillaJson.get("invites");
        for(Object obj : array) {
            Invite invite = new Invite();
            invite.loadSerializedData(new XillaJson((JSONObject)obj));
            invites.add(invite);
        }

    }
}
