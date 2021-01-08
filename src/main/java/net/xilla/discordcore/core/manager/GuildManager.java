package net.xilla.discordcore.core.manager;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.manager.Manager;

import java.util.HashMap;
import java.util.Map;

public abstract class GuildManager<T extends  GuildManagerObject> {

    @Getter
    private Map<String, Manager<String, T>> managers = new HashMap<>();

    @Getter
    private String name;

    @Getter
    private String folder;

    private Class<T> clazz = null;

    public GuildManager(String name) {
        this(name, "");
    }

    public GuildManager(String name, String folder) {
        this.name = name;
        this.folder = folder;
    }

    public GuildManager(String name, Class<T> clazz) {
        this(name, "", clazz);
    }

    public GuildManager(String name, String folder, Class<T> clazz) {
        this.clazz = clazz;
        this.name = name;
        this.folder = folder;
    }

    public Manager<String, T> getManager(String guildID) {
        if(!managers.containsKey(guildID)) {
            managers.put(guildID, new Manager<String, T>(guildID + "-" + name, folder + guildID + "/" + name + ".json", clazz) {
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
        }
        return managers.get(guildID);
    }

    public Manager<String, T> getManager(Guild guild) {
        return getManager(guild.getId());
    }

    protected abstract void load();

    protected abstract void objectAdded(String guildID, T object);

    protected abstract void objectRemoved(String guildID, T object);

}
