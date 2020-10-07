package net.xilla.discordcore.command.template.type;

import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.core.library.json.XillaJson;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.command.template.TemplateCommand;
import net.xilla.discordcore.embed.EmbedStorage;
import net.xilla.discordcore.embed.JSONEmbed;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EmbedCommand extends TemplateCommand {

    private JSONEmbed jsonEmbed;

    public EmbedCommand(String module, String name, String[] activators, String description, String usage, String title, String text, String permission) {
        super(module, name, activators, description, usage, permission, (data) ->
                new CoreCommandResponse(data).setEmbed(new EmbedBuilder().setTitle(title)
                .setDescription(text).setColor(Color.decode(DiscordCore.getInstance().getSettings()
                .getEmbedColor())).build()));
        this.jsonEmbed = new JSONEmbed("embed", new EmbedBuilder().setTitle(title)
                .setDescription(text).setColor(Color.decode(DiscordCore.getInstance().getSettings()
                        .getEmbedColor())));
    }

    public EmbedCommand(JSONObject map) {
        super(
                map.get("module").toString(),
                map.get("name").toString(),
                map.get("activators").toString().split(","),
                map.get("description").toString(), map.get("usage").toString(),
                map.get("permission"),
                (data) -> new CoreCommandResponse(data).setEmbed(new EmbedBuilder().setTitle(map.get("title").toString())
                        .setDescription(map.get("text").toString()).setColor(Color.decode(DiscordCore.getInstance().getSettings()
                        .getEmbedColor())).build())
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

}
