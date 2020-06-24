package net.xilla.discordcore.command.response;

import com.tobiassteely.tobiasapi.command.data.CommandData;
import com.tobiassteely.tobiasapi.command.response.CommandResponse;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class CoreCommandResponse extends CommandResponse {

    private MessageEmbed embed;

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
}
