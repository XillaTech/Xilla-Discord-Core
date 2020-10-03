package net.xilla.discordcore.core.server;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordCore;

public class ServerManager extends Manager<CoreServer> {

    public ServerManager() {
        super("Servers","servers.json");
        DiscordCore.getInstance().addExecutor(this::startManager);
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

    }

    @Override
    protected void objectAdded(CoreServer server) {

    }

    @Override
    protected void objectRemoved(CoreServer server) {

    }
}
