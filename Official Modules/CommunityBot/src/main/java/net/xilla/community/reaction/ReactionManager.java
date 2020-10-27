package net.xilla.community.reaction;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.community.punishment.PunishmentUser;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.manager.GuildManager;
import org.json.simple.JSONObject;

public class ReactionManager extends GuildManager<ReactionRole> {

    public ReactionManager() {
        super("Reactions");

        DiscordCore.getInstance().addExecutor(() -> {
            load();
        });
    }

    @Override
    protected void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<ReactionRole> manager = getManager(guild);

            for(Object obj : manager.getConfig().getJson().getJson().values()) {
                ReactionRole role = new ReactionRole();
                role.loadSerializedData(new XillaJson((JSONObject)obj));
                manager.put(role);
            }
        }
    }

    @Override
    protected void objectAdded(String s, ReactionRole reactionRole) {

    }

    @Override
    protected void objectRemoved(String s, ReactionRole reactionRole) {

    }
}
