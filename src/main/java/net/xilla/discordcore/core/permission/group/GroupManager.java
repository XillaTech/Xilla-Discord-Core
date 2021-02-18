package net.xilla.discordcore.core.permission.group;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.manager.GuildManager;
import net.xilla.discordcore.library.DiscordAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class GroupManager extends GuildManager<DiscordGroup> {

    private Map<String, List<DiscordGroup>> groupCache = new ConcurrentHashMap<>();
    private Map<String, List<DiscordGroup>> serverCache = new ConcurrentHashMap<>();

    public GroupManager() {
        super("Groups", "servers/permissions/", DiscordGroup.class);

        setThreads(DiscordAPI.getCoreSetting().getGroupThreads());

        DiscordCore.getInstance().addExecutor(() -> {
            Logger.log(LogLevel.INFO, "Starting group manager", getClass());
            load();
            Logger.log(LogLevel.INFO, "Started group manager", getClass());
            Logger.log(LogLevel.INFO, "Starting user manager", getClass());
            DiscordAPI.getUserManager().load();
            Logger.log(LogLevel.INFO, "Started user manager", getClass());
        });
    }

    public List<DiscordGroup> getGroupsByServer(String id) {
        return serverCache.get(id);
    }

    public List<DiscordGroup> getGroupsByName(String name) {
        return groupCache.get(name);
    }

    @Override
    public void load() {
        super.load();

        for(Guild guild : DiscordCore.getInstance().getBot().getGuilds()) {
            Manager<String, DiscordGroup> manager = getManager(guild);

            DiscordGroup defaultGroup = manager.get("default");
            if(defaultGroup == null) {
                DiscordGroup group = new DiscordGroup("default", "Member", guild.getId(), new ArrayList<>());
                manager.put(group);
            }

            manager.save();
        }
    }

    @Override
    protected void objectAdded(String guildID, DiscordGroup staff) {
        if (!groupCache.containsKey(staff.getGroupName())) {
            groupCache.put(staff.getGroupName(), new Vector<>());
        }
        groupCache.get(staff.getGroupName()).add(staff);

        if (!serverCache.containsKey(staff.getServerID())) {
            serverCache.put(staff.getServerID(), new Vector<>());
        }
        serverCache.get(staff.getServerID()).add(staff);
    }

    @Override
    protected void objectRemoved(String guildID, DiscordGroup object) {
        groupCache.get(object.getGroupName()).remove(object);
        serverCache.get(object.getServerID()).remove(object);

        if(groupCache.get(object.getGroupName()).size() == 0) {
            groupCache.remove(object.getGroupName());
        }
        if(serverCache.get(object.getServerID()).size() == 0) {
            serverCache.remove(object.getServerID());
        }
    }

}
