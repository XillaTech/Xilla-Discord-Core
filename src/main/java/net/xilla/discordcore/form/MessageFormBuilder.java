package net.xilla.discordcore.form;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.library.CoreObject;
import net.xilla.discordcore.library.DiscordAPI;
import net.xilla.discordcore.form.form.Form;
import net.xilla.discordcore.form.form.FormBuilder;
import net.xilla.discordcore.form.form.FormMessageEvent;

public class MessageFormBuilder implements CoreObject, FormBuilder {

    private String name;
    private String ownerID;
    private TextChannel textChannel;
    private MessageEmbed embed;
    private FormMessageEvent event;

    // String name, String ownerID, TextChannel channel, MessageEmbed embed, FormMessageEvent messageEvent

    public MessageFormBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MessageFormBuilder setOwnerID(String ownerID) {
        this.ownerID = ownerID;
        return this;
    }

    public MessageFormBuilder setTextChannel(String id) {
        this.textChannel = getBot().getTextChannelById(id);
        return this;
    }

    public MessageFormBuilder setTextChannel(TextChannel textChannel) {
        this.textChannel = textChannel;
        return this;
    }

    public MessageFormBuilder setMessage(MessageEmbed embed) {
        this.embed = embed;
        return this;
    }

    public MessageFormBuilder setFormMessageEvent(FormMessageEvent event) {
        this.event = event;
        return this;
    }

    public Form build(String channelID) {
        Message message = textChannel.sendMessage(embed).complete();
        return new Form(name, message, ownerID, null, channelID, null, event);
    }

    public void register(String channelID) {
        DiscordAPI.getFormManager().addForm(build(channelID));
    }

    public void register(Form form) {
        DiscordAPI.getFormManager().addForm(form);
    }

    public String getType() {
        return "Message";
    }

}
