package net.xilla.discordcore.commandsystem;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.api.Data;
import net.xilla.discordcore.api.Log;

import java.util.ArrayList;
import java.util.List;

public class CommandResponse {

    private String title = null;
    private int status;
    private List<String> response;

    public CommandResponse(String title, int status, List<String> response) {
        this.title = title;
        this.status = status;
        this.response = response;
    }

    public CommandResponse(String title, int status) {
        this.title = title;
        this.status = status;
        this.response = new ArrayList<>();
    }

    public List<String> getResponse() {
        return response;
    }

    public void sendResponse(MessageReceivedEvent event) {
        if(event == null) {
            if(title == null) {
                Log.sendMessage(0, new Data().getLineBreak());
                for(String line : response) {
                    Log.sendMessage(0, line);
                }
                Log.sendMessage(0, new Data().getLineBreak());
            } else {
                Log.sendMessage(0, new Data().getLineBreak());
                Log.sendMessage(0, title);
                Log.sendMessage(0, new Data().getLineBreak());
                for(String line : response) {
                    Log.sendMessage(0, line);
                }
                Log.sendMessage(0, new Data().getLineBreak());
            }
        } else {
            EmbedBuilder myEmbed = new Data().createEmbed(title, new Data().parseStringListNoDelimiter(0, response));
            event.getTextChannel().sendMessage(myEmbed.build()).queue();
        }
    }

    public void sendResponse(MessageReceivedEvent event, long time) {
        if(event == null) {
            Log.sendMessage(1, "Cannot send timed command line messages.");
            sendResponse(null);
        } else {
            new Thread(() -> {
                EmbedBuilder myEmbed = new Data().createEmbed(title, new Data().parseStringListNoDelimiter(0, response));
                Message message = event.getTextChannel().sendMessage(myEmbed.build()).complete();

                try {
                    Thread.sleep(time);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                message.delete().queue();
            }).start();
        }
    }

}
