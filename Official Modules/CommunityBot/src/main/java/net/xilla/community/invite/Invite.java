package net.xilla.community.invite;

import lombok.Getter;
import lombok.Setter;
import net.xilla.core.library.json.SerializedObject;
import net.xilla.core.library.json.XillaJson;

public class Invite implements SerializedObject {

    @Getter
    @Setter
    private String invitedUserID;

    @Getter
    @Setter
    private String userID;

    @Getter
    @Setter
    private String inviteCode;

    @Getter
    @Setter
    private long time;

    public Invite(String invitedUserID, String userID, String inviteCode, long time) {
        this.invitedUserID = invitedUserID;
        this.userID = userID;
        this.inviteCode = inviteCode;
        this.time = time;
    }

    public Invite() {

    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("invitedUserID", invitedUserID);
        json.put("userID", userID);
        json.put("inviteCode", inviteCode);
        json.put("time", time);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        this.invitedUserID = xillaJson.get("invitedUserID");
        this.userID = xillaJson.get("userID");
        this.inviteCode = xillaJson.get("inviteCode");
        this.time = Long.parseLong(xillaJson.get("time").toString());
    }
}
