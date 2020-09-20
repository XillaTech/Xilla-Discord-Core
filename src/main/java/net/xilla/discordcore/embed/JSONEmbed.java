package net.xilla.discordcore.embed;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.simple.JSONObject;

import java.awt.*;

public class JSONEmbed {

    @Getter
    private EmbedBuilder embedBuilder;
    @Getter
    private String name;

    public JSONEmbed(String name, EmbedBuilder embedBuilder) {
        this.embedBuilder = embedBuilder;
        this.name = name.replace(" ", "_");
    }

    public JSONEmbed(String name, JSONObject json) {
        this.embedBuilder = new EmbedBuilder();
        this.name = name.replace(" ", "_");

        if (json.containsKey("title")) {
            embedBuilder.setTitle(json.get("title").toString());
        }

        if (json.containsKey("desc")) {
            embedBuilder.setDescription(json.get("desc").toString());
        }

        if (json.containsKey("author")) {
            if (json.containsKey("url")) {
                embedBuilder.setAuthor(json.get("author").toString(), json.get("url").toString());
            } else {
                embedBuilder.setAuthor(json.get("author").toString());
            }
        }

        if (json.containsKey("color")) {
            embedBuilder.setColor(Integer.parseInt(json.get("color").toString()));
        }

        if (json.containsKey("footer")) {
            embedBuilder.setFooter(json.get("footer").toString());
        }

        if (json.containsKey("image")) {
            embedBuilder.setImage(json.get("image").toString());
        }
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        MessageEmbed embed = build();

        if(!embedBuilder.isEmpty()) {

            if (embed.getTitle() != null) {
                json.put("title", embed.getTitle());
            }

            if (embed.getDescription() != null) {
                json.put("desc", embed.getDescription());
            }

            if (embed.getAuthor() != null) {
                json.put("author", embed.getAuthor());
            }

            if (embed.getColor() != null) {
                json.put("color", embed.getColorRaw());
            }

            if (embed.getFooter() != null) {
                json.put("footer", embed.getFooter());
            }

            if (embed.getUrl() != null) {
                json.put("url", embed.getUrl());
            }

            if (embed.getImage() != null) {
                json.put("image", embed.getImage().getUrl());
            }

        }

        return json;
    }

    public MessageEmbed build() {
        return embedBuilder.build();
    }

}
