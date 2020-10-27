package net.xilla.community.invite;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.community.giveaway.Giveaway;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.manager.GuildManager;
import org.json.simple.JSONObject;

public class InviteManager extends GuildManager<InviteUser> {

    public InviteManager() {
        super("Invites");

        DiscordCore.getInstance().addExecutor(() -> {
            load();
        });
    }

    @Override
    protected void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<InviteUser> manager = getManager(guild);

            for(Object obj : manager.getConfig().getJson().getJson().values()) {
                InviteUser inviteUser = new InviteUser();
                inviteUser.loadSerializedData(new XillaJson((JSONObject)obj));
                manager.put(inviteUser);
            }
        }
    }

    @Override
    protected void objectAdded(String s, InviteUser inviteUser) {

    }

    @Override
    protected void objectRemoved(String s, InviteUser inviteUser) {

    }
}
