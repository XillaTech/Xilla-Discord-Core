package net.xilla.discordcore.core.command.handler;

import net.xilla.discordcore.core.command.permission.group.PermissionGroup;
import net.xilla.discordcore.core.command.permission.user.PermissionUser;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SpigotUser implements PermissionUser {

    private CommandSender player;

    public SpigotUser(CommandSender player) {
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
