package net.xilla.discordcore.core.permission.user;

import net.xilla.discordcore.core.manager.GuildManager;
import net.xilla.discordcore.core.permission.PermissionAPI;
import net.xilla.discordcore.library.DiscordAPI;

public class UserManager extends GuildManager<DiscordUser> {

    public UserManager() {
        super("Users", "servers/permissions/", DiscordUser.class);
        setThreads(DiscordAPI.getCoreSetting().getUserThreads());
    }

    @Override
    protected void objectAdded(String guildID, DiscordUser object) {

    }

    @Override
    protected void objectRemoved(String guildID, DiscordUser object) {
        PermissionAPI.getUserCache().remove(object.getUserIdentifier());
    }

}
