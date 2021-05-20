package net.xilla.discord.reflection;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.reflection.storage.StorageReflection;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.manager.embed.DiscordEmbed;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

public class EmbedBuilderReflection extends StorageReflection<EmbedBuilder> {

    public EmbedBuilderReflection() {
        super(EmbedBuilder.class);
    }

    @Override
    public EmbedBuilder loadFromSerializedData(ConfigFile configFile, Object o, Field field, Object o1) {
        JSONObject json = (JSONObject) o1;

        DiscordEmbed embed = new DiscordEmbed();
        embed.loadSerializedData(new XillaJson(json));

        return embed.getEmbedBuilder();
    }

    @Override
    public Object getSerializedData(ConfigFile configFile, Object o, Field field, EmbedBuilder embedBuilder) {
        return new DiscordEmbed("Temporary", embedBuilder).getSerializedData().getJson();
    }

}
