package net.xilla.discordcore.command.template.type;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.core.library.json.XillaJson;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.template.TemplateCommand;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.library.embed.JSONEmbed;
import org.json.simple.JSONObject;

public class EmbedCommand extends TemplateCommand {

    private JSONEmbed jsonEmbed;

    public EmbedCommand(String module, String name, String[] activators, String description, String usage, String title, String text, String permission) {
        super(module, name, activators, description, usage, permission, (data) -> {
            EmbedBuilder builder = new EmbedBuilder();

            if(data.get() instanceof MessageReceivedEvent) {
                builder = DiscordCore.getInstance().getServerSettings().getEmbed((MessageReceivedEvent)data.get());
            }

            builder.setTitle(title).setDescription(text);
            return new CoreCommandResponse(data).setEmbed(builder.build());
        });
        this.jsonEmbed = new JSONEmbed("embed", new EmbedBuilder().setTitle(title)
                .setDescription(text));
    }

    public EmbedCommand(JSONObject map) {
        super(
                map.get("module").toString(),
                map.get("name").toString(),
                map.get("activators").toString().split(","),
                map.get("description").toString(), map.get("usage").toString(),
                map.get("permission"),
                (data) -> {
                    EmbedBuilder builder = new EmbedBuilder();

                    if(data.get() instanceof MessageReceivedEvent) {
                        builder = DiscordCore.getInstance().getServerSettings().getEmbed((MessageReceivedEvent)data.get());
                    }

                    builder.setTitle(map.get("title").toString()).setDescription(map.get("text").toString());
                    return new CoreCommandResponse(data).setEmbed(builder.build());
                }
        );
        this.jsonEmbed = new JSONEmbed("embed", (JSONObject)map.get("embed"));
    }

    @Override
    public XillaJson getSerializedData() {
        JSONObject map = new JSONObject();
        map.put("name", getKey());
        map.put("module", getModule());
        map.put("description", getDescription());
        map.put("usage", getUsage());
        map.put("permission", getPermission());

        map.put("type", "Embed");

        StringBuilder activatorsParsed = new StringBuilder();
        for(int i = 0; i < getActivators().length; i++) {
            activatorsParsed.append(getActivators()[i]);
            if(i != getActivators().length - 1) {
                activatorsParsed.append(",");
            }
        }

        map.put("activators", activatorsParsed.toString());

        map.put("embed", jsonEmbed);

        return new XillaJson(new JSONObject(map));
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }

    @Override
    public void setData(String text) {
        jsonEmbed.getEmbedBuilder().setDescription(text);
    }

    @Override
    public String getData() {
        return jsonEmbed.getEmbedBuilder().build().getDescription();
    }

}
