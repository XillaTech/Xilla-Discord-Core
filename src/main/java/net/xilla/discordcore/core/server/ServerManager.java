package net.xilla.discordcore.core.server;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.xilla.discordcore.DiscordCore;

public class ServerManager extends ManagerParent<CoreServer> {

    public ServerManager() {
        super("XDC.Server", false, "servers.json", new ServerEventHandler());
        DiscordCore.getInstance().addExecutor(this::startManager);
    }

    public void startManager() {
        reload();
        DiscordCore.getInstance().getPlatform().getCoreWorker().start();
    }

}
