package net.xilla.community.punishment;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.core.manager.GuildManagerObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PunishmentUser extends GuildManagerObject {

    @Getter
    private List<Punishment> punishments = new ArrayList<>();

    public PunishmentUser(String userID, Guild guild) {
        super(userID, "Punishments", guild);
    }

    public PunishmentUser() {
        super("", "Punishments", "");
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("userID", getKey());
        json.put("serverID", getGuildID());

        JSONArray array = new JSONArray();
        for(Punishment punishment : punishments) {
            array.add(punishment.getSerializedData());
        }

        json.put("punishments", array);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        setGuildID(xillaJson.get("serverID"));
        setKey(xillaJson.get("userID"));

        JSONArray array = xillaJson.get("punishments");
        for(Object obj : array) {
            Punishment punishment = new Punishment();
            punishment.loadSerializedData(new XillaJson((JSONObject)obj));
        }
    }
}
