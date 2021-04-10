package net.xilla.discord.manager.permission.group;

import net.xilla.core.library.manager.ManagerObject;
import net.xilla.discord.api.permission.PermissionGroup;

import java.util.ArrayList;
import java.util.List;

public class DiscordGroup extends ManagerObject implements PermissionGroup {

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public String getName() {
        return "Temp";
    }

    @Override
    public List<String> getPermissions() {
        return new ArrayList<>();
    }

}
