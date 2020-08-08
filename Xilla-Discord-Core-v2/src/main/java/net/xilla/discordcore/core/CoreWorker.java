package net.xilla.discordcore.core;

import com.tobiassteely.tobiasapi.api.worker.Worker;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.server.CoreServer;

public class CoreWorker extends Worker {

    public CoreWorker() {
        super(60000);
        DiscordCore.getInstance().addExecutor(this::start);
    }

    @Override
    public Boolean runWorker(long start) {

        for(Guild guild : DiscordCore.getInstance().getBot().getGuilds()) {
            DiscordCore.getInstance().getPlatform().getServerManager().addServer(new CoreServer(guild));
        }

        if(DiscordCore.getInstance().getCoreSetting().isClearOldGuilds()) {
            long lastCheckTime = DiscordCore.getInstance().getCoreSetting().getLastCheckTime();
            long checkTimeSpeed = DiscordCore.getInstance().getCoreSetting().getCheckTime();
            long checkTimeToDelete = DiscordCore.getInstance().getCoreSetting().getClearOldGuildTime();
            if(System.currentTimeMillis() - lastCheckTime  <= checkTimeSpeed * 1000) {
                for (CoreServer coreServer : DiscordCore.getInstance().getPlatform().getServerManager().getList()) {
                    if(System.currentTimeMillis() - coreServer.getLastUpdated() > checkTimeToDelete * 1000) {
                        DiscordCore.getInstance().getPlatform().getServerManager().removeObject(coreServer.getKey());
                    }
                }
            }
        }
        DiscordCore.getInstance().getPlatform().getServerManager().save();

        return true;
    }

}
