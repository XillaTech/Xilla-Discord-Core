package net.xilla.discordcore.core.permission.user;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.core.manager.GuildManager;
import org.json.simple.JSONObject;

public class UserManager extends GuildManager<DiscordUser> {

    public UserManager() {
        super("Users", "servers/permissions/");
    }

    @Override
    protected void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<DiscordUser> manager = getManager(guild);

            for(Object obj : manager.getConfig().getJson().getJson().values()) {
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

    }
}
