package net.xilla.community.punishment;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.community.invite.InviteUser;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.manager.GuildManager;
import org.json.simple.JSONObject;

public class PunishmentManager extends GuildManager<PunishmentUser> {

    public PunishmentManager() {
        super("Punishments");

        DiscordCore.getInstance().addExecutor(() -> {
            load();
        });
    }

    @Override
    protected void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<PunishmentUser> manager = getManager(guild);

            for(Object obj : manager.getConfig().getJson().getJson().values()) {
                PunishmentUser punishmentUser = new PunishmentUser();
                punishmentUser.loadSerializedData(new XillaJson((JSONObject)obj));
                manager.put(punishmentUser);
            }
        }
    }

    @Override
    protected void objectAdded(String s, PunishmentUser punishmentUser) {

    }

    @Override
    protected void objectRemoved(String s, PunishmentUser punishmentUser) {

    }

}
