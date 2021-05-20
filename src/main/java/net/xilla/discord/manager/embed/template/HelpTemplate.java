package net.xilla.discord.manager.embed.template;

import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discord.manager.embed.DiscordEmbed;

import java.awt.*;

public class HelpTemplate extends DiscordEmbed {

    public HelpTemplate() {
        super("Help", new EmbedBuilder());

        getEmbedBuilder().setTitle("%bot-name% Commands");
        getEmbedBuilder().setFooter("Run %prefix%(command) to execute that command!");
        getEmbedBuilder().setColor(Color.decode("#e207ff"));
    }

}
