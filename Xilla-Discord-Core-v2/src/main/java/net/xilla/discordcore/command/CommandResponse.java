package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.Log;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.DiscordCore;

import java.awt.*;


public class CommandResponse {

    private String text;
    private EmbedBuilder embed;

    public CommandResponse(EmbedBuilder embed) {
        this.embed = embed;
        this.text = null;

        embed.setColor(Color.decode(DiscordCore.getInstance().getSettings().getEmbedColor()));
    }

    public CommandResponse(String text) {
        this.text = text;
        this.embed = null;
    }

    public void send(TextChannel channel) {
        if(channel == null) {
            send();
        } else {
            if(text != null) {
                channel.sendMessage(text).queue();
            } else {
                channel.sendMessage(embed.build()).queue();
            }
        }
    }

    public void send() {
        if(text != null) {
            Log.sendMessage(0, text);
        } else {
            Log.sendMessage(2, "Something is trying to send an embed named (" + embed.build().getTitle() + ")" + " to console!");
        }
    }

}
