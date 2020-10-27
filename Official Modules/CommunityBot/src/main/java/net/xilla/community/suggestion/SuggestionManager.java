package net.xilla.community.suggestion;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.community.review.Review;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.manager.GuildManager;
import org.json.simple.JSONObject;

public class SuggestionManager extends GuildManager<Suggestion> {

    public SuggestionManager() {
        super("Suggestions");

        DiscordCore.getInstance().addExecutor(() -> {
            load();
        });
    }

    @Override
    protected void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<Suggestion> manager = getManager(guild);

            for(Object obj : manager.getConfig().getJson().getJson().values()) {
                Suggestion suggestion = new Suggestion();
                suggestion.loadSerializedData(new XillaJson((JSONObject)obj));
                manager.put(suggestion);
            }
        }
    }

    @Override
    protected void objectAdded(String s, Suggestion suggestion) {

    }

    @Override
    protected void objectRemoved(String s, Suggestion suggestion) {

    }
}
