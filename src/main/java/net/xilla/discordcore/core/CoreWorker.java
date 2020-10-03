package net.xilla.discordcore.core;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.worker.Worker;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.server.CoreServer;

import java.util.ArrayList;

public class CoreWorker extends Worker {

    public CoreWorker() {
        super("XDC.Core",60000);
    }

    @Override
    public void runWorker(long start) {

        for(Guild guild : DiscordCore.getInstance().getBot().getGuilds()) {
            if(DiscordCore.getInstance().getPlatform().getServerManager().getData().containsKey(guild.getId())) {
                CoreServer obj = DiscordCore.getInstance().getPlatform().getServerManager().get(guild.getId());
                obj.update(guild);
            } else {
                DiscordCore.getInstance().getPlatform().getServerManager().put(new CoreServer(guild));
            }
        }

        if(DiscordCore.getInstance().getCoreSetting().isClearOldGuilds()) {
            long lastCheckTime = DiscordCore.getInstance().getCoreSetting().getLastCheckTime();
            long checkTimeSpeed = DiscordCore.getInstance().getCoreSetting().getCheckTime();
            long checkTimeToDelete = DiscordCore.getInstance().getCoreSetting().getClearOldGuildTime();
            if(System.currentTimeMillis() - lastCheckTime  <= checkTimeSpeed * 1000) {
                for (CoreServer coreServer : new ArrayList<>(DiscordCore.getInstance().getPlatform().getServerManager().getData().values())) {
                    if(System.currentTimeMillis() - coreServer.getLastUpdated() > checkTimeToDelete * 1000) {
                        DiscordCore.getInstance().getPlatform().getServerManager().remove(coreServer.getKey());
                        DiscordCore.getInstance().getCommandSettings().removeServer(coreServer.getKey());
                    }
                }
            }
        }
        DiscordCore.getInstance().getPlatform().getServerManager().save();
    }
}
