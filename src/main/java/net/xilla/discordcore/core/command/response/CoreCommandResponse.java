package net.xilla.discordcore.core.command.response;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.core.command.action.PostCommandAction;

public class CoreCommandResponse extends CommandResponse {

    private MessageEmbed embed;

    private PostCommandAction action = null;

    public CoreCommandResponse(CommandData data) {
        super(data);
    }

    public MessageEmbed getEmbed() {
        return embed;
    }

    public CoreCommandResponse setEmbed(MessageEmbed embed) {
        this.embed = embed;
        setTitle(embed.getTitle());
        setDescription(embed.getDescription());
        return this;
    }

    public CoreCommandResponse setData(CommandData data) {
        super.setData(data);
        return this;
    }

    public CommandData getData() {
        return super.getData();
    }

    public CoreCommandResponse setTitle(String title) {
        super.setTitle(title);
        return this;
    }

    public CoreCommandResponse setDescription(String description) {
        super.setDescription(description);
        return this;
    }

    public CoreCommandResponse setInputType(String inputType) {
        super.setInputType(inputType);
        return this;
    }

    public CoreCommandResponse setAction(PostCommandAction action) {
        this.action = action;
        return this;
    }

    public void sentMessage(Message message) {
        if(action != null) {
            action.run(message);
        }
    }

}
