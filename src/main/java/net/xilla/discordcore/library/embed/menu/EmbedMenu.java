package net.xilla.discordcore.library.embed.menu;

import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.core.library.manager.ObjectInterface;

public interface EmbedMenu extends ObjectInterface {

    void send(TextChannel channel);

    void terminate();

    boolean isRunning();

    long getStartTime();

}
