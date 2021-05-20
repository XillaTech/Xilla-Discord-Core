package net.xilla.discord.manager.placeholder;

import net.xilla.core.library.manager.Manager;
import net.xilla.discord.api.command.Command;
import net.xilla.discord.api.placeholder.Placeholder;
import net.xilla.discord.api.placeholder.PlaceholderProcessor;
import net.xilla.discord.manager.command.discord.DiscordCommand;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderManager extends Manager<String, DiscordPlaceholder> implements PlaceholderProcessor {

    public PlaceholderManager() {
        super("DiscordPlaceholder", "placeholders.json", DiscordPlaceholder.class);
    }

    @Override
    public void putObject(Placeholder object) {
        put((DiscordPlaceholder) object);
    }

    @Override
    public void removeObject(Placeholder object) {
        remove(object.getName());
    }

    @Override
    public List<Placeholder> listObjects() {
        return new ArrayList<>(iterate());
    }

}
