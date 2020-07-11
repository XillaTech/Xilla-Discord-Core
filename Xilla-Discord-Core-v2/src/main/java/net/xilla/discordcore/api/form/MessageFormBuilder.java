package net.xilla.discordcore.api.form;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.api.DiscordAPI;

public class MessageFormBuilder extends CoreObject implements FormBuilder {

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

    public Form build() {
        Message message = textChannel.sendMessage(embed).complete();
        return new Form(name, message, ownerID, null, null, event);
    }

    public void register() {
        DiscordAPI.getInstance().getFormManager().addForm(build());
    }

    public void register(Form form) {
        DiscordAPI.getInstance().getFormManager().addForm(form);
    }

    public String getType() {
        return "Message";
    }

}
