package net.xilla.discord.library;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.manager.ObjectInterface;
import net.xilla.discord.api.GuildProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuildManager<Key, Value extends ObjectInterface> implements GuildProcessor<Key, Value> {

    private final Map<String, Manager<Key, Value>> managers = new ConcurrentHashMap<>();

    private final String name;
    private final String folder;
    private final Class<Value> clazz;

    public GuildManager(String name, String folder, Class<Value> clazz) {
        this.name = name;
        this.folder = folder;
        this.clazz = clazz;
    }

    @Override
    public List<String> getGuildIDs() {
        return new ArrayList<>(managers.keySet());
    }

    @Override
    public List<Value> listObjects(Guild guild) {
        return getManager(guild).iterate();
    }

    @Override
    public void putObject(Guild guild, Value object) {
        getManager(guild).put(object);
    }

    @Override
    public void removeObject(Guild guild, Value object) {
        getManager(guild).remove(object);
    }

    @Override
    public void save(String guildID) {
        getManager(guildID).save();
    }

    @Override
    public Value get(Guild guild, Key o) {
        return getManager(guild).get(o);
    }

    public Manager<Key, Value> getManager(String guildID) {
        if(!managers.containsKey(guildID)) {
            managers.put(guildID, new Manager<>(name, folder + "/" + guildID + ".jsonf", clazz));
        }
        return managers.get(guildID);
    }

    public Manager<Key, Value> getManager(Guild guild) {
        return getManager(guild.getId());
    }


}
