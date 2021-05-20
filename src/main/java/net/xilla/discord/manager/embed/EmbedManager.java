package net.xilla.discord.manager.embed;

import net.xilla.core.library.manager.Manager;
import net.xilla.discord.api.embed.EmbedFormat;
import net.xilla.discord.api.embed.EmbedProcessor;
import net.xilla.discord.api.placeholder.Placeholder;
import net.xilla.discord.manager.embed.template.HelpTemplate;
import net.xilla.discord.manager.embed.template.PermissionTemplate;
import net.xilla.discord.manager.placeholder.DiscordPlaceholder;

import java.util.ArrayList;
import java.util.List;

public class EmbedManager extends Manager<String, DiscordEmbed> implements EmbedProcessor {

    public EmbedManager() {
        super("DiscordEmbed", "embed-templates.json", DiscordEmbed.class);
    }

    @Override
    public void load() {
        super.load();

        if(!containsKey("Help")) {
            put(new HelpTemplate());
        }
        if(!containsKey("Permission")) {
            put(new PermissionTemplate());
        }
    }

    @Override
    public void putObject(EmbedFormat object) {
        put((DiscordEmbed) object);
    }

    @Override
    public void removeObject(EmbedFormat object) {
        remove(object.getName());
    }

    @Override
    public List<EmbedFormat> listObjects() {
        return new ArrayList<>(iterate());
    }

}
