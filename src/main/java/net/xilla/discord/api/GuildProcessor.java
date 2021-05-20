package net.xilla.discord.api;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.NotNull;
import net.xilla.core.library.Nullable;
import net.xilla.core.library.manager.ObjectInterface;
import net.xilla.discord.DiscordCore;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic interface to add and remove data
 * from a higher-level processor.
 */
public interface GuildProcessor<Key, Value extends ObjectInterface> {

    /**
     * Returns a list of every available guild ID.
     * Whether it's active or inactive.
     *
     * @return Guild ID List
     */
    @NotNull
    List<String> getGuildIDs();

    /**
     * Returns a list of all active guilds
     *
     * @return Active Guilds
     */
    @NotNull
    default List<Guild> getGuilds() {
        List<String> guildIDs = getGuildIDs();
        List<Guild> guilds = new ArrayList<>();

        for(String guildID : guildIDs) {
            Guild guild = DiscordCore.getInstance().getJda().getGuildById(guildID);
            if(guild != null) {
                guilds.add(guild);
            }
        }

        return guilds;
    }

    /**
     * Attempts to grab the object from the processor and returns it.
     *
     * @param guild Guild Object
     * @param key Object Key
     * @return Object
     */
    @Nullable
    Value get(@NotNull Guild guild, @NotNull Key key);

    /**
     * Used to get a list of objects from the processor
     *
     * @param guild Guild Object
     * @return List of Objects
     */
    List<Value> listObjects(@NotNull Guild guild);

    /**
     * Puts the object into the implementation storage
     *
     * @param guild Guild Object
     * @param object Object
     */
    void putObject(@NotNull Guild guild, @NotNull Value object);

    /**
     * Attempts to grab the object from the processor.
     * If the object with that key does not exist, the
     * function returns null. Otherwise it will attempt
     * to remove it from the processor then it will
     * return the item.
     *
     * @param guild Guild Object
     * @param key Object Key
     * @return Object
     */
    @Nullable
    default Value removeObject(@NotNull Guild guild, @NotNull Key key) {
        Value item = get(guild, key);

        if(item == null) return null;

        removeObject(guild, item);
        return item;
    }

    /**
     * Used to remove an object from the processor
     *
     * @param guild Guild Object
     * @param object Object
     */
    void removeObject(@NotNull Guild guild, @NotNull Value object);

    /**
     * Used to save the processor
     */
    default void save() {
        for(String guildID : getGuildIDs()) {
            save(guildID);
        }
    }

    /**
     * Used to save a guilds processor
     */
    default void save(Guild guild) {
        save(guild.getId());
    }

    /**
     * Used to save a guilds processor by the ID
     */
    void save(String guildID);

}
