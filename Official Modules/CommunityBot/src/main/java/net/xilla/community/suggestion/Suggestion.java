package net.xilla.community.suggestion;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.core.manager.GuildManagerObject;

import java.util.UUID;

public class Suggestion extends GuildManagerObject {

    private String userID;
    private String text;
    private long time;
    private int upvotes;
    private int downvotes;

    public Suggestion(String messageID, String userID, String text, Guild guild) {
        super(messageID, "Suggestions", guild);

        this.userID = userID;
        this.text = text;
        this.time = System.currentTimeMillis();
        this.upvotes = 0;
        this.downvotes = 0;
    }

    public Suggestion() {
        super("", "Suggestions", "");
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("serverID", getGuildID());
        json.put("messageID", getKey());
        json.put("userID", userID);
        json.put("text", text);
        json.put("upvotes", upvotes);
        json.put("downvotes", downvotes);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        setKey(xillaJson.get("messageID"));
        setGuildID(xillaJson.get("serverID"));

        this.userID = xillaJson.get("userID");
        this.text = xillaJson.get("text");
        this.upvotes = Integer.parseInt(xillaJson.get("upvotes").toString());
        this.downvotes = Integer.parseInt(xillaJson.get("downvotes").toString());
    }
}
