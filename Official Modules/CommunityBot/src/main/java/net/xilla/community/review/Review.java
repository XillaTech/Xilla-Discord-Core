package net.xilla.community.review;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.core.manager.GuildManagerObject;

public class Review extends GuildManagerObject {

    private String userID;
    private String text;
    private long time;

    public Review(String messageID, String userID, String text, Guild guild) {
        super(messageID, "Reviews", guild);
        this.userID = userID;
        this.text = text;
        this.time = System.currentTimeMillis();
    }

    public Review() {
        super("", "Reviews", "");
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("serverID", getGuildID());
        json.put("messageID", getKey());
        json.put("userID", userID);
        json.put("text", text);
        json.put("time", time);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        setGuildID(xillaJson.get("serverID"));
        setKey(xillaJson.get("messageID"));

        this.userID = xillaJson.get("userID");
        this.text = xillaJson.get("text");
        this.time = Long.parseLong(xillaJson.get("time").toString());
    }

}
