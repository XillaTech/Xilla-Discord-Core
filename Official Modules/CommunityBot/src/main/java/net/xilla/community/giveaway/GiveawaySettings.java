package net.xilla.community.giveaway;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.embed.JSONEmbed;
import net.xilla.discordcore.settings.GuildSettings;
import net.xilla.discordcore.settings.Settings;
import org.json.simple.JSONObject;

public class GiveawaySettings extends GuildSettings {

    private static GiveawaySettings instance = new GiveawaySettings();

    public static GiveawaySettings getInstance() {
        return instance;
    }

    public GiveawaySettings() {
        super("Giveaways", "modules/Giveaways/settings/");

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(DiscordAPI.getColor());
        embedBuilder.setTitle("Giveaway (%amount%x %name%)");
        embedBuilder.setDescription("There is %time% left! React with %emoji%.");
        embedBuilder.setFooter("Ends at %end-date%.");
        JSONEmbed defaultEmbed = new JSONEmbed("defaultEmbed", embedBuilder);
        setDefault("embed", defaultEmbed.toJSON());

        EmbedBuilder embedBuilder2 = new EmbedBuilder();
        embedBuilder2.setColor(DiscordAPI.getColor());
        embedBuilder2.setTitle("Giveaway (%amount%x %name%)");
        embedBuilder2.setDescription("This giveaway is complete. %winner% has won!");
        embedBuilder2.setFooter("Ended at %end-date%.");
        JSONEmbed defaultEmbed2 = new JSONEmbed("defaultEmbed", embedBuilder2);
        setDefault("finished-embed", defaultEmbed2.toJSON());
    }

    public JSONEmbed getEmbed(Guild guild) {
        return new JSONEmbed("embed", (JSONObject)get(guild, "embed"));
    }

    public JSONEmbed getFinishedEmbed(Guild guild) {
        return new JSONEmbed("embed", (JSONObject)get(guild, "finished-embed"));
    }

}
