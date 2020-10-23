package net.xilla.discordcore.core.permission.group;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.manager.GuildManager;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class GroupManager extends GuildManager<DiscordGroup> {

    private Map<String, List<DiscordGroup>> groupCache = new ConcurrentHashMap<>();
    private Map<String, List<DiscordGroup>> serverCache = new ConcurrentHashMap<>();

    public GroupManager() {
        super("Groups", "servers/permissions/");

        DiscordCore.getInstance().addExecutor(this::load);
    }

    public List<DiscordGroup> getGroupsByServer(String id) {
        return serverCache.get(id);
    }

    public List<DiscordGroup> getGroupsByName(String name) {
        return groupCache.get(name);
    }

    @Override
    public void load() {
        for(Guild guild : DiscordCore.getInstance().getBot().getGuilds()) {
            Manager<DiscordGroup> manager = getManager(guild);

            DiscordGroup defaultGroup = manager.get("default");
            if(defaultGroup == null) {
                DiscordGroup group = new DiscordGroup("default", "Member", guild.getId(), new ArrayList<>());
                manager.put(group);
            }

            for(Object obj : manager.getConfig().getJson().getJson().values()) {
                JSONObject json = (JSONObject) obj;
                DiscordGroup group = new DiscordGroup(json);
                manager.put(group);
            }

            manager.save();
        }
    }

    @Override
    protected void objectAdded(String guildID, DiscordGroup staff) {
        if (!groupCache.containsKey(staff.getName())) {
            groupCache.put(staff.getName(), new Vector<>());
        }
        groupCache.get(staff.getName()).add(staff);

        if (!serverCache.containsKey(staff.getServerID())) {
            serverCache.put(staff.getServerID(), new Vector<>());
        }
        serverCache.get(staff.getServerID()).add(staff);
    }

    @Override
    protected void objectRemoved(String guildID, DiscordGroup object) {
        groupCache.get(object.getName()).remove(object);
        serverCache.get(object.getServerID()).remove(object);

        if(groupCache.get(object.getName()).size() == 0) {
            groupCache.remove(object.getName());
        }
        if(serverCache.get(object.getServerID()).size() == 0) {
            serverCache.remove(object.getServerID());
        }
    }

}
