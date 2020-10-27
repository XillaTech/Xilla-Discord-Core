package net.xilla.community.economy;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.community.giveaway.Giveaway;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.manager.GuildManager;
import net.xilla.discordcore.core.manager.GuildManagerObject;
import org.json.simple.JSONObject;

public class EconomyManager extends GuildManager<EconomyUser> {

    public EconomyManager() {
        super("Economy", "modules/Economy/data/");

        new EconomyCommands();

        DiscordCore.getInstance().addExecutor(this::load);
    }

    @Override
    protected void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<EconomyUser> manager = getManager(guild);

            for(Object obj : manager.getConfig().getJson().getJson().values()) {
                EconomyUser economyUser = new EconomyUser();
                economyUser.loadSerializedData(new XillaJson((JSONObject)obj));
                manager.put(economyUser);
            }
        }
    }

    @Override
    protected void objectAdded(String s, EconomyUser economyUser) {

    }

    @Override
    protected void objectRemoved(String s, EconomyUser economyUser) {

    }

}
