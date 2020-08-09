package com.tobiassteely.utility.punishment;

import net.xilla.discordcore.api.settings.Settings;
import org.json.simple.JSONArray;

public class PunishmentSettings extends Settings {

    public PunishmentSettings() {
        super("Punishment", "modules/Utility/punishment-settings.json");

        getConfig().loadDefault("muted-users", new JSONArray());
        getConfig().save();
    }

    private JSONArray getMutedUsers() {
        return getConfig().getList("muted-users");
    }

    public boolean isMuted(String userID) {
        JSONArray array = getMutedUsers();
        return array.contains(userID);
    }


    public void addMutedUser(String userID) {
        JSONArray array = getMutedUsers();
        array.add(userID);
        getConfig().set("muted-users", array);
        getConfig().save();
    }

    public void removeMutedUser(String userID) {
        JSONArray array = getMutedUsers();
        array.remove(userID);
        getConfig().set("muted-users", array);
        getConfig().save();
    }

}
