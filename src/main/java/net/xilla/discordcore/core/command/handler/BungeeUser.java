package net.xilla.discordcore.core.command.handler;

import net.dv8tion.jda.api.entities.Guild;
import net.md_5.bungee.api.CommandSender;
import net.xilla.discordcore.core.command.permission.group.PermissionGroup;
import net.xilla.discordcore.core.command.permission.user.PermissionUser;

import java.util.List;

public class BungeeUser implements PermissionUser {

    private CommandSender player;

    public BungeeUser(CommandSender player) {
        this.player = player;
    }

    @Override
    public List<PermissionGroup> getGroups() {
        return null;
    }

    @Override
    public boolean hasPermission(Guild guild, String s) {
        return player.hasPermission(s);
    }


    @Override
    public String getUserIdentifier() {
        return player.getName();
    }

}
