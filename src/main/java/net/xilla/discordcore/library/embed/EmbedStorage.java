package net.xilla.discordcore.library.embed;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.core.library.config.Config;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.discordcore.DiscordCore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmbedStorage extends ManagerObject {

    private Config config;
    @Getter
    private Map<String, JSONEmbed> embedMap;

    @Override
    public XillaJson getSerializedData() {
        return null;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }

    public EmbedStorage(String name, String configName) {
        super(name.toLowerCase(), "Embeds");
        this.config = new Config(configName);
        this.embedMap = new ConcurrentHashMap<>();

        load();

        DiscordCore.getInstance().getPlatform().getEmbedManager().put(this);
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
        for(Object key : config.getJson().getJson().keySet()) {

            if(key.toString().equalsIgnoreCase("file-extension")) {
                continue;
            }

            String name = key.toString();
            embedMap.put(name, new JSONEmbed(name, config.getJSON(name)));
        }
    }

}
