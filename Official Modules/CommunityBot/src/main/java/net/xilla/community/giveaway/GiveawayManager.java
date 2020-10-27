package net.xilla.community.giveaway;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.manager.GuildManager;
import org.json.simple.JSONObject;

public class GiveawayManager extends GuildManager<Giveaway> {

    private GiveawayWorker worker;

    public GiveawayManager() {
        super("Giveaways", "modules/Giveaways/data/");

        this.worker = new GiveawayWorker();

        DiscordCore.getInstance().getBot().addEventListener(new GiveawayHandler());

        DiscordCore.getInstance().addExecutor(() -> {
            load();
            worker.start();
        });
    }

    @Override
    protected void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<Giveaway> manager = getManager(guild);

            for(Object obj : manager.getConfig().getJson().getJson().values()) {
                Giveaway giveaway = new Giveaway();
                giveaway.loadSerializedData(new XillaJson((JSONObject)obj));
                manager.put(giveaway);
            }
        }
    }

    @Override
    protected void objectAdded(String s, Giveaway giveaway) {

    }

    @Override
    protected void objectRemoved(String s, Giveaway giveaway) {

    }
}
