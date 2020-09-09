package net.xilla.discordcore.embed;

import com.tobiassteely.tobiasapi.api.manager.ManagerEventExecutor;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;

public class EmbedManager extends ManagerParent<EmbedStorage> {

    public EmbedManager() {
        super("XDC.Embed", true);
    }

}
