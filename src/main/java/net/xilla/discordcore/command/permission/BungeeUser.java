package net.xilla.discordcore.command.permission;

import com.tobiassteely.tobiasapi.command.permission.group.PermissionGroup;
import com.tobiassteely.tobiasapi.command.permission.user.PermissionUser;
import net.md_5.bungee.api.CommandSender;

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
    public boolean hasPermission(String s) {
        return player.hasPermission(s);
    }

    @Override
    public PermissionGroup getPrimaryGroup() {
        return null;
    }

    @Override
    public String getUserIdentifier() {
        return null;
    }

}
