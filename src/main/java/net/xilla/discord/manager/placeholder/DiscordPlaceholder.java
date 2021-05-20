package net.xilla.discord.manager.placeholder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.discord.api.placeholder.Placeholder;

public abstract class DiscordPlaceholder extends ManagerObject implements Placeholder {

    public DiscordPlaceholder(String name) {
        super(name, "DiscordPlaceholder");
    }

    public DiscordPlaceholder() {}

    @Override
    public String getName() {
        return toString();
    }
}
