package net.xilla.discordcore.command.type.template.type;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.CommandResponse;
import net.xilla.discordcore.command.type.template.TemplateCommand;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmbedCommand extends TemplateCommand {

    private String title;
    private String text;
    private String footer;

    public EmbedCommand(String name, String[] activators, String description, String usage, int staffLevel, String title, String text, String footer) {
        super(name, activators, description, usage, staffLevel);
        this.title = title;
        this.text = text;
        this.footer = footer;
    }

    public EmbedCommand(Map<String, String> map) {
        super(map.get("name"), map.get("activators").split(","), map.get("description"), map.get("usage"), Integer.parseInt(map.get("staffLevel")));
        this.title = map.get("title");
        this.text = map.get("text");
        this.footer = map.get("footer");
    }

    @Override
    public CommandResponse runTemplate(String[] args, MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if(title != null) {
            embedBuilder.setTitle(title);
        }
        if(text != null) {
            embedBuilder.setDescription(text);
        }
        if(footer != null) {
            embedBuilder.setTitle(footer);
        }
        return new CommandResponse(embedBuilder);
    }

    @Override
    public JSONObject getJSON() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", getKey());
        map.put("type", "Embed");
        map.put("staffLevel", "" + getStaffLevel());
        map.put("description", getDescription());
        map.put("usage", getUsage());

        StringBuilder activatorsParsed = new StringBuilder();
        for(int i = 0; i < getActivators().length; i++) {
            activatorsParsed.append(getActivators()[i]);
            if(i != getActivators().length - 1) {
                activatorsParsed.append(",");
            }
        }

        map.put("activators", activatorsParsed.toString());

        if(title != null) {
            map.put("title", title);
        }
        if(text != null) {
            map.put("text", text);
        }
        if(footer != null) {
            map.put("footer", footer);
        }

        return new JSONObject(map);
    }
}
