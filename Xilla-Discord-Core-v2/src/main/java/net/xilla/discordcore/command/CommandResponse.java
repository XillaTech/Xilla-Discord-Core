package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.Log;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.platform.Platform;

import java.awt.*;


public class CommandResponse {

    private String text;
    private EmbedBuilder embed;

    public CommandResponse(EmbedBuilder embed) {
        this.embed = embed;
        this.text = null;

        this.embed.setColor(Color.decode(DiscordCore.getInstance().getSettings().getEmbedColor()));
    }

    public CommandResponse(EmbedBuilder embed, boolean forcedColor) {
        this.embed = embed;
        this.text = null;

        if(forcedColor) {
            this.embed.setColor(Color.decode(DiscordCore.getInstance().getSettings().getEmbedColor()));
        }
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
        String prefix = "";
        if(!DiscordCore.getInstance().getPlatform().getType().equals(Platform.getPlatform.STANDALONE.getName())) {
            prefix = "[DiscordCore] ";
        }
        if(text != null) {
            Log.sendMessage(0, prefix + text);
        } else {
            Log.sendMessage(2, prefix + "Something is trying to send an embed named (" + embed.build().getTitle() + ")" + " to console!");
        }
    }

}
