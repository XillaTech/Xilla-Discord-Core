package net.xilla.discordcore.library.form.form;

import net.dv8tion.jda.api.entities.Message;
import net.xilla.discordcore.library.form.form.reaction.FormReactionEvent;

import java.util.List;

public class Form {

    private String name;
    private Message message;
    private String formOwner;
    private List<FormOption> options;
    private FormReactionEvent reactionEvent;
    private FormMessageEvent messageEvent;
    private String channelID;

    public Form(String name, Message message, String formOwner, List<FormOption> options, String channelID, FormReactionEvent reactionEvent, FormMessageEvent messageEvent) {
        this.name = name;
        this.message = message;
        this.formOwner = formOwner;
        this.options = options;
        this.reactionEvent = reactionEvent;
        this.messageEvent = messageEvent;
        this.channelID = channelID;
    }

    public Message getMessage() {
        return message;
    }

    public String getFormOwner() {
        return formOwner;
    }

    public FormReactionEvent getReactionEvent() {
        return reactionEvent;
    }

    public FormMessageEvent getMessageEvent() {
        return messageEvent;
    }

    public String getName() {
        return name;
    }

    public List<FormOption> getFormOptions() {
        return options;
    }

    public String getChannelID() {
        return channelID;
    }
}
