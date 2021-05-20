package net.xilla.discord.reflection;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.reflection.storage.StorageReflection;
import net.xilla.discord.DiscordCore;

import java.lang.reflect.Field;

public class UserReflection extends StorageReflection<User> {

    public UserReflection() {
        super(User.class);
    }

    @Override
    public User loadFromSerializedData(ConfigFile configFile, Object o, Field field, Object o1) {
        return DiscordCore.getInstance().getJda().getUserById(o1.toString());
    }

    @Override
    public Object getSerializedData(ConfigFile configFile, Object o, Field field, User user) {
        return user.getId();
    }

}
