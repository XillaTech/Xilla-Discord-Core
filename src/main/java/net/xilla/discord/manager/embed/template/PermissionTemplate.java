package net.xilla.discord.manager.embed.template;

import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discord.manager.embed.DiscordEmbed;

import java.awt.*;

public class PermissionTemplate extends DiscordEmbed {

    public PermissionTemplate() {
        super("Permission", new EmbedBuilder());

        getEmbedBuilder().setTitle("%bot-name% Permissions");
        getEmbedBuilder().setFooter("Run -perms (subcommand) to execute");
        getEmbedBuilder().setColor(Color.decode("#e207ff"));
    }

}
