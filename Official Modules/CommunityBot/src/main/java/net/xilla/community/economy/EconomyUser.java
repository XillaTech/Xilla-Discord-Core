package net.xilla.community.economy;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.core.manager.GuildManagerObject;

public class EconomyUser extends GuildManagerObject {

    @Getter
    @Setter
    private int balance;

    @Getter
    @Setter
    private long lastRedeemedWork;

    @Getter
    @Setter
    private long lastRedeemedMessage;

    @Getter
    @Setter
    private long lastRedeemedDaily;

    @Getter
    @Setter
    private long lastRedeemedWeekly;

    @Getter
    @Setter
    private long lastRedeemedMonthly;

    /**
     * Default constructor for creating users.
     *
     * @param userID The user's ID
     * @param guild The guild the user is from
     */
    public EconomyUser(String userID, Guild guild) {
        super(userID, "Economy", guild);

        this.balance = 0;
    }

    /**
     * The default constructor for loading users
     * from storage. Make sure to load the serialized
     * data or you will have a bad day.
     */
    public EconomyUser() {
        super("", "Economy", "");
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("userID", getKey());
        json.put("serverID", getGuildID());
        json.put("balance", balance);
        json.put("lastRedeemedWork", lastRedeemedWork);
        json.put("lastRedeemedDaily", lastRedeemedDaily);
        json.put("lastRedeemedWeekly", lastRedeemedWeekly);
        json.put("lastRedeemedMonthly", lastRedeemedMonthly);
        json.put("lastRedeemedMessage", lastRedeemedMessage);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        this.balance = Integer.parseInt(xillaJson.get("balance").toString());
        setKey(xillaJson.get("balance"));
        setGuildID(xillaJson.get("serverID"));
        this.lastRedeemedWork = Long.parseLong(xillaJson.get("lastRedeemedWork").toString());
        this.lastRedeemedDaily = Long.parseLong(xillaJson.get("lastRedeemedDaily").toString());
        this.lastRedeemedWeekly = Long.parseLong(xillaJson.get("lastRedeemedWeekly").toString());
        this.lastRedeemedMonthly = Long.parseLong(xillaJson.get("lastRedeemedMonthly").toString());
        this.lastRedeemedMessage = Long.parseLong(xillaJson.get("lastRedeemedMessage").toString());
    }
}
