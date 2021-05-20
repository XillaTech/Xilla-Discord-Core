package net.xilla.discord.manager.placeholder.object;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.manager.placeholder.DiscordPlaceholder;
import net.xilla.discord.setting.DiscordSettings;

public class HelpPlaceholder extends DiscordPlaceholder {

    public HelpPlaceholder() {
        super("Help");
    }

    @Override
    public String injectSystem(String string) {
        return null;
    }

    @Override
    public String injectUser(Member member, String string) {
        return null;
    }

    @Override
    public EmbedBuilder injectSystem(EmbedBuilder builder) {
        MessageEmbed embed = builder.build();

        DiscordSettings settings = DiscordCore.getInstance().getSettings();

        String title = embed.getTitle();
        if(title != null) {
            title = title.replaceAll("%bot-name%", DiscordCore.getInstance().toString());
            title = title.replaceAll("%prefix%", settings.getPrefix());
            builder.setTitle(title);
        }

        MessageEmbed.Footer footer = embed.getFooter();
        if(footer != null && footer.getText() != null) {
            builder.setFooter(footer.getText().replaceAll("%bot-name%", DiscordCore.getInstance().toString()));
            builder.setFooter(footer.getText().replaceAll("%prefix%", settings.getPrefix()));
        }

        return builder;
    }

    @Override
    public EmbedBuilder injectUser(Member member, EmbedBuilder builder) {
        builder = injectSystem(builder);

        return builder;
    }
}
