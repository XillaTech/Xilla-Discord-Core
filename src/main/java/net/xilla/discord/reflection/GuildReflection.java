package net.xilla.discord.reflection;

import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.reflection.storage.StorageReflection;
import net.xilla.discord.DiscordCore;

import java.lang.reflect.Field;

public class GuildReflection extends StorageReflection<Guild> {

    public GuildReflection() {
        super(Guild.class);
    }

    @Override
    public Guild loadFromSerializedData(ConfigFile configFile, Object o, Field field, Object o1) {
        return DiscordCore.getInstance().getJda().getGuildById(o1.toString());
    }

    @Override
    public Object getSerializedData(ConfigFile configFile, Object o, Field field, Guild guild) {
        return guild.getId();
    }

}
