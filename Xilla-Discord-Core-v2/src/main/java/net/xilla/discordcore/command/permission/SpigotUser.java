package net.xilla.discordcore.command.permission;

import com.tobiassteely.tobiasapi.command.permission.group.PermissionGroup;
import com.tobiassteely.tobiasapi.command.permission.user.PermissionUser;
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
