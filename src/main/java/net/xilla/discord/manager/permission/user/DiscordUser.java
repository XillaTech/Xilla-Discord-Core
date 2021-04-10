package net.xilla.discord.manager.permission.user;

import net.xilla.core.library.manager.ManagerObject;
import net.xilla.discord.api.permission.PermissionGroup;
import net.xilla.discord.api.permission.PermissionUser;

import java.util.ArrayList;
import java.util.List;

public class DiscordUser extends ManagerObject implements PermissionUser {

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public String getName() {
        return "Temp";
    }

    @Override
    public List<PermissionGroup> getGroups() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getPermissions() {
        return new ArrayList<>();
    }

}
