package net.xilla.discordcore.command.template.type;

import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.core.library.json.XillaJson;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.command.template.TemplateCommand;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EmbedCommand extends TemplateCommand {

    private String text;

    public EmbedCommand(String module, String name, String[] activators, String description, String usage, String title, String text, String permission) {
        super(module, name, activators, description, usage, permission, (data) ->
                new CoreCommandResponse(data).setEmbed(new EmbedBuilder().setTitle(title)
                .setDescription(text).setColor(Color.decode(DiscordCore.getInstance().getSettings()
                .getEmbedColor())).build()));
        this.text = text;
    }

    public EmbedCommand(Map<String, String> map) {
        super(
                map.get("module"),
                map.get("name"),
                map.get("activators").split(","),
                map.get("description"), map.get("usage"),
                map.get("permission"),
                (data) -> new CoreCommandResponse(data).setEmbed(new EmbedBuilder().setTitle(map.get("title"))
                        .setDescription(map.get("text")).setColor(Color.decode(DiscordCore.getInstance().getSettings()
                        .getEmbedColor())).build())
        );
        this.text = map.get("text");
    }


    @Override
    public XillaJson getSerializedData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", getKey());
        map.put("module", getModule());
        map.put("description", getDescription());
        map.put("usage", getUsage());
        map.put("permission", getPermission());
        map.put("type", "Text");

        StringBuilder activatorsParsed = new StringBuilder();
        for(int i = 0; i < getActivators().length; i++) {
            activatorsParsed.append(getActivators()[i]);
            if(i != getActivators().length - 1) {
                activatorsParsed.append(",");
            }
        }

        map.put("activators", activatorsParsed.toString());

        map.put("text", text);

        return new XillaJson(new JSONObject(map));
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }

}
