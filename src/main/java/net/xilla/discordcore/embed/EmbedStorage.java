package net.xilla.discordcore.embed;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.config.Config;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmbedStorage extends ManagerObject {

    private Config config;
    @Getter
    private Map<String, JSONEmbed> embedMap;

    public EmbedStorage(String name, String configName) {
        super(name.toLowerCase());
        this.config = DiscordCore.getInstance().getConfigManager().getConfig(configName);
        this.embedMap = new ConcurrentHashMap<>();

        load();

        DiscordCore.getInstance().getEmbedManager().addObject(this);
    }

    public void addEmbed(String name, EmbedBuilder embedBuilder) {
        embedMap.put(name.replace(" ", "_"), new JSONEmbed(name, embedBuilder));
    }

    public void addEmbed(JSONEmbed jsonEmbed) {
        embedMap.put(jsonEmbed.getName(), jsonEmbed);
    }

    public void removeEmbed(String name) {
        embedMap.remove(name);
    }

    public JSONEmbed getEmbed(String name) {
        return embedMap.get(name.replace(" ", "_"));
    }

    public void save() {
        for(String name : embedMap.keySet()) {
            config.set(name, embedMap.get(name).toJSON());
        }
        config.save();
    }

    public void load() {
        for(Object key : config.getJSON().keySet()) {
            String name = key.toString();
            embedMap.put(name, new JSONEmbed(name, config.getJSON(name)));
        }
    }

}
