package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.CommandResponse;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.DiscordCore;
import org.jetbrains.annotations.NotNull;


public class CoreCommandResponse extends TobiasObject implements CommandResponse {

    @Override
    public void send(String title, String text, String inputType) {
        if(inputType.equals(CommandResponse.command_line_input)) {
            getLog().sendMessage(0, title, text);
        } else {
            TextChannel channel = DiscordCore.getInstance().getBot().getTextChannelById(inputType);
            channel.sendMessage(title + "\n" + text).queue();
        }
    }

    @Override
    public void send(String text, String inputType) {
        if(inputType.equals(CommandResponse.command_line_input)) {
            getLog().sendMessage(0, text);
        } else {
            TextChannel channel = DiscordCore.getInstance().getBot().getTextChannelById(inputType);
            channel.sendMessage(text).queue();
        }
    }

    public void send(EmbedBuilder embedBuilder, String inputType) {
        MessageEmbed embed = embedBuilder.build();
        if(inputType.equals(CommandResponse.command_line_input)) {
            getLog().sendMessage(0, embed.getTitle(), embed.getDescription());
        } else {
            TextChannel channel = DiscordCore.getInstance().getBot().getTextChannelById(inputType);
            channel.sendMessage(embed).queue();
        }
    }

}
