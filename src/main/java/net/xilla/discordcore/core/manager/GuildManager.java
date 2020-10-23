package net.xilla.discordcore.core.manager;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.manager.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class GuildManager<T extends  GuildManagerObject> {

    @Getter
    private Map<String, Manager<T>> managers = new HashMap<>();

    @Getter
    private Map<String, Object> defaults = new HashMap<>();

    @Getter
    private String name;

    @Getter
    private String folder;

    public GuildManager(String name) {
        this(name, "");
    }

    public GuildManager(String name, String folder) {
        this.name = name;
        this.folder = folder;
    }

    public void addDefault(String key, Object obj) {
        defaults.put(key, obj);
    }

    public void getDefault(String key) {
        defaults.get(key);
    }

    public Manager<T> getManager(String guildID) {
        if(!managers.containsKey(guildID)) {
            managers.put(guildID, new Manager<T>(guildID + "-" + name, folder + guildID + "/" + name + ".json") {
                @Override
                protected void load() {
                    GuildManager.this.load();
                }

                @Override
                protected void objectAdded(T object) {
                    GuildManager.this.objectAdded(guildID, object);
                }

                @Override
                protected void objectRemoved(T object) {
                    GuildManager.this.objectRemoved(guildID, object);
                }
            });

            Manager<T> manager = managers.get(guildID);
            for(String key : new ArrayList<>(defaults.keySet())) {
                manager.getConfig().setDefault(key, defaults.get(key));
            }
        }
        return managers.get(guildID);
    }

    public Manager<T> getManager(Guild guild) {
        return getManager(guild.getId());
    }

    protected abstract void load();

    protected abstract void objectAdded(String guildID, T object);

    protected abstract void objectRemoved(String guildID, T object);

}
