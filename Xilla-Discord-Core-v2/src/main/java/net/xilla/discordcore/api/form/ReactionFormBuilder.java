package net.xilla.discordcore.api.form;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.api.DiscordAPI;
import net.xilla.discordcore.api.form.form.Form;
import net.xilla.discordcore.api.form.form.FormBuilder;
import net.xilla.discordcore.api.form.form.FormOption;
import net.xilla.discordcore.api.form.form.reaction.FormReactionEvent;

import java.util.List;

public class ReactionFormBuilder extends CoreObject implements FormBuilder {

    private String name;
    private String ownerID;
    private TextChannel textChannel;
    private MessageEmbed embed;
    private FormReactionEvent event;
    private List<FormOption> options;

    // String name, String ownerID, TextChannel channel, MessageEmbed embed, FormMessageEvent messageEvent

    public ReactionFormBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ReactionFormBuilder setOwnerID(String ownerID) {
        this.ownerID = ownerID;
        return this;
    }

    public ReactionFormBuilder setTextChannel(String id) {
        this.textChannel = getBot().getTextChannelById(id);
        return this;
    }

    public ReactionFormBuilder setTextChannel(TextChannel textChannel) {
        this.textChannel = textChannel;
        return this;
    }

    public ReactionFormBuilder setMessage(MessageEmbed embed) {
        this.embed = embed;
        return this;
    }

    public ReactionFormBuilder setFormReactionEvent(FormReactionEvent event) {
        this.event = event;
        return this;
    }

    public ReactionFormBuilder setOptions(List<FormOption> options) {
        this.options = options;
        return this;
    }

    public Form build(String channelID) {
        Message message = textChannel.sendMessage(embed).complete();

        for(FormOption option : options) {
            message.addReaction(option.getEmote()).queue();
        }

        return new Form(name, message, ownerID, options, channelID, event, null);
    }

    public void register(String channelID) {
        DiscordAPI.getInstance().getFormManager().addForm(build(channelID));
    }

    public void register(Form form) {
        DiscordAPI.getInstance().getFormManager().addForm(form);
    }

    public String getType() {
        return "Reaction";
    }

}
