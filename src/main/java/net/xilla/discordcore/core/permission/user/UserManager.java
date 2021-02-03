package net.xilla.discordcore.core.permission.user;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.core.manager.GuildManager;
import net.xilla.discordcore.core.permission.PermissionAPI;
import net.xilla.discordcore.library.DiscordAPI;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserManager extends GuildManager<DiscordUser> {

    private ExecutorService executor;

    public UserManager() {
        super("Users", "servers/permissions/");

        this.executor = Executors.newFixedThreadPool(16);
    }

    @Override
    public void load() {
        for(Guild guild : DiscordAPI.getBot().getGuilds()) {
            Manager<String, DiscordUser> manager = getManager(guild);

            for(Object key : new ArrayList<>(manager.getConfig().getJson().getJson().keySet())) {
                if(key.toString().equals("file-extension")) {
                    manager.getConfig().getJson().getJson().remove(key);
                    continue;
                }
                executor.submit(() -> {
                    Object obj = manager.getConfig().getJson().getJson().get(key);

                    JSONObject json = (JSONObject) obj;

                    DiscordUser user = new DiscordUser(guild);
                    json.put("key", key.toString());
                    json.put("manager", manager.getKey().toString());
                    user.loadSerializedData(new XillaJson(json));
                    if(user.getMember() != null) {
                        manager.put(user);
                    }
                });
            };
        }
    }

    @Override
    protected void objectAdded(String guildID, DiscordUser object) {

    }

    @Override
    protected void objectRemoved(String guildID, DiscordUser object) {
        PermissionAPI.getUserCache().remove(object.getUserIdentifier());
    }

}
