package net.xilla.community.review;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.community.reaction.ReactionRole;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.manager.GuildManager;
import org.json.simple.JSONObject;

public class ReviewManager extends GuildManager<Review> {

    public ReviewManager() {
        super("Reviews");

        DiscordCore.getInstance().addExecutor(() -> {
            load();
        });
    }

    @Override
    protected void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<Review> manager = getManager(guild);

            for(Object obj : manager.getConfig().getJson().getJson().values()) {
                Review review = new Review();
                review.loadSerializedData(new XillaJson((JSONObject)obj));
                manager.put(review);
            }
        }
    }

    @Override
    protected void objectAdded(String s, Review review) {

    }

    @Override
    protected void objectRemoved(String s, Review review) {

    }

}
