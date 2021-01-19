package net.xilla.discordcore.core.permission.user;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.library.DiscordAPI;
import net.xilla.discordcore.core.manager.GuildManager;
import net.xilla.discordcore.core.permission.PermissionAPI;
import org.json.simple.JSONObject;

public class UserManager extends GuildManager<DiscordUser> {

    public UserManager() {
        super("Users", "servers/permissions/");
    }

    @Override
    public void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<String, DiscordUser> manager = getManager(guild);

            for(Object key : manager.getConfig().getJson().getJson().keySet()) {

                if(key.toString().equalsIgnoreCase("file-extension")) {
                    continue;
                }

                Object obj = manager.getConfig().getJson().getJson().get(key);

                JSONObject json = (JSONObject) obj;

                DiscordUser user = new DiscordUser(guild);
                user.loadSerializedData(new XillaJson(json));
                manager.put(user);
            }
        }
    }

    @Override
    protected void objectAdded(String guildID, DiscordUser object) {

    }

    @Override
    protected void objectRemoved(String guildID, DiscordUser object) {
        PermissionAPI.getUserCache().remove(object.getUserIdentifier());
    }

}
