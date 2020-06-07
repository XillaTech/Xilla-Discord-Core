package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.CommandResponse;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import org.jetbrains.annotations.NotNull;


public class CoreCommandResponse extends TobiasObject implements CommandResponse {

    @Override
    public void send(String title, String text, String inputType, Object... data) {
        if(inputType.equals(CoreCommandExecutor.discord_input)) {
            MessageReceivedEvent event = (MessageReceivedEvent)data[0];
            event.getTextChannel().sendMessage(title + "\n" + text).queue();
        } else {
            getLog().sendMessage(0, title, text);
        }
    }

    @Override
    public void send(String text, String inputType, Object... data) {
        if(inputType.equals(CoreCommandExecutor.discord_input)) {
            MessageReceivedEvent event = (MessageReceivedEvent)data[0];
            event.getTextChannel().sendMessage(text).queue();
        } else {
            getLog().sendMessage(0, text);
        }
    }

    public void send(EmbedBuilder embedBuilder, String inputType, Object... data) {
        MessageEmbed embed = embedBuilder.build();
        if(inputType.equals(CoreCommandExecutor.discord_input)) {
            MessageReceivedEvent event = (MessageReceivedEvent)data[0];
            event.getTextChannel().sendMessage(embed).queue();
        } else {
            getLog().sendMessage(0, embed.getTitle(), embed.getDescription());
        }
    }

}
