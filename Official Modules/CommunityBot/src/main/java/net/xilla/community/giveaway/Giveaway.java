package net.xilla.community.giveaway;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.core.manager.GuildManagerObject;
import net.xilla.discordcore.library.embed.JSONEmbed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Giveaway extends GuildManagerObject {

    @Getter
    private String name;

    @Getter
    private int amount;

    @Getter
    private String channelID;

    @Getter
    private String emoji;

    @Getter
    private long startTime;

    @Getter
    private long duration;

    @Getter
    private List<String> users = new ArrayList<>();

    @Getter
    @Setter
    private boolean active = true;

    public Giveaway(TextChannel channel, long duration, int amount, String name, String emoji) {
        super("", "Giveaways", channel.getGuild());

        this.duration = duration;
        this.amount = amount;
        this.emoji = emoji;
        this.name = name;
        this.startTime = System.currentTimeMillis();
        this.channelID = channel.getId();

        JSONEmbed embed = GiveawaySettings.getInstance().getEmbed(channel.getGuild());

        String title = embed.getEmbedBuilder().build().getTitle();
        String description = embed.getEmbedBuilder().build().getDescription();

        String footer = null;
        if(embed.getEmbedBuilder().build().getFooter() != null) {
            footer = embed.getEmbedBuilder().build().getFooter().getText();
        }

        if (title != null) {
            embed.getEmbedBuilder().setTitle(injectPlaceholders(title));
        }

        if(description != null) {
            embed.getEmbedBuilder().setDescription(injectPlaceholders(description));
        }

        if(footer != null) {
            embed.getEmbedBuilder().setFooter(injectPlaceholders(footer));
        }

        Message message = channel.sendMessage(embed.build()).complete();

        message.addReaction(EmojiParser.parseToUnicode(emoji)).complete();

        setKey(message.getId());
    }

    public String injectPlaceholders(String str) {
        return str
                .replace("%name%", name)
                .replace("%amount%", amount + "")
                .replace("%start-date%", new Date(startTime).toString())
                .replace("%end-date%", new Date(startTime + duration).toString())
                .replace("%time%", convertToTime((startTime + duration) - System.currentTimeMillis()))
                .replace("%emoji%", EmojiParser.parseToUnicode(emoji));
    }

    public Giveaway() {
        super("", "Giveaways", "");
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("name", name);
        json.put("startTime", startTime);
        json.put("amount", amount);
        json.put("channelID", channelID);
        json.put("duration", duration);
        json.put("users", users);
        json.put("emoji", emoji);
        json.put("messageID", getKey());
        json.put("active", active);
        json.put("serverID", getGuildID());

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        this.emoji = xillaJson.get("emoji");
        this.name = xillaJson.get("name");
        this.startTime = Long.parseLong(xillaJson.get("startTime").toString());
        this.amount = Integer.parseInt(xillaJson.get("amount").toString());
        this.channelID = xillaJson.get("channelID");
        this.duration = Long.parseLong(xillaJson.get("duration").toString());
        this.users.addAll(xillaJson.get("users"));
        this.active = xillaJson.get("active");
        setKey(xillaJson.get("messageID"));

        if(xillaJson.containsKey("serverID")) {
            setGuildID(xillaJson.get("serverID"));
        } else {
            TextChannel channel = DiscordAPI.getBot().getTextChannelById(channelID);

            if(channel == null) {
                setGuildID("None");
            } else {
                setGuildID(channel.getGuild().getId());
            }
        }
    }

}
