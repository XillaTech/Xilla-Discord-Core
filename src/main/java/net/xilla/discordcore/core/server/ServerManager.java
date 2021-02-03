package net.xilla.discordcore.core.server;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

public class ServerManager extends Manager<String, CoreServer> {

    public ServerManager() {
        super("Servers","servers.json");
        DiscordCore.getInstance().addExecutor(() -> {
            Logger.log(LogLevel.DEBUG, "Starting server manager", getClass());
            startManager();
        });
    }

    public void startManager() {
        DiscordCore.getInstance().getPlatform().getCoreWorker().start();

        for(Guild guild : DiscordCore.getInstance().getBot().getGuilds()) {
            if(getData().containsKey(guild.getId())) {
                CoreServer obj = get(guild.getId());
                obj.update(guild);
            } else {
                put(new CoreServer(guild));
            }
        }
    }

    @Override
    public void load() {
        for(Object key : getConfig().getJson().getJson().keySet()) {
            JSONObject data = getConfig().getJson().get(key.toString());
            put(new CoreServer(data));
        }
    }

    @Override
    protected void objectAdded(CoreServer server) {

    }

    @Override
    protected void objectRemoved(CoreServer server) {

    }
}
