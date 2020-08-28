package net.xilla.discordcore.core;

import com.tobiassteely.tobiasapi.api.worker.Worker;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.server.CoreServer;

public class CoreWorker extends Worker {

    public CoreWorker() {
        super("XDC.Core",60000);
    }

    @Override
    public Boolean runWorker(long start) {

        for(Guild guild : DiscordCore.getInstance().getBot().getGuilds()) {
            if(DiscordCore.getInstance().getPlatform().getServerManager().contains(guild.getId())) {
                CoreServer obj = DiscordCore.getInstance().getPlatform().getServerManager().getObject(guild.getId());
                obj.update(guild);
            } else {
                DiscordCore.getInstance().getPlatform().getServerManager().addObject(new CoreServer(guild));
            }
        }

        if(DiscordCore.getInstance().getCoreSetting().isClearOldGuilds()) {
            long lastCheckTime = DiscordCore.getInstance().getCoreSetting().getLastCheckTime();
            long checkTimeSpeed = DiscordCore.getInstance().getCoreSetting().getCheckTime();
            long checkTimeToDelete = DiscordCore.getInstance().getCoreSetting().getClearOldGuildTime();
            if(System.currentTimeMillis() - lastCheckTime  <= checkTimeSpeed * 1000) {
                for (CoreServer coreServer : DiscordCore.getInstance().getPlatform().getServerManager().getList()) {
                    if(System.currentTimeMillis() - coreServer.getLastUpdated() > checkTimeToDelete * 1000) {
                        DiscordCore.getInstance().getPlatform().getServerManager().removeObject(coreServer.getKey());
                        DiscordCore.getInstance().getCommandSettings().removeServer(coreServer.getKey());
                    }
                }
            }
        }
        DiscordCore.getInstance().getPlatform().getServerManager().save();

        return true;
    }

}
