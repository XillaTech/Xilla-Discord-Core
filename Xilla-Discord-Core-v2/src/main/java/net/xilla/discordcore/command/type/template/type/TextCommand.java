package net.xilla.discordcore.command.type.template.type;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.CommandResponse;
import net.xilla.discordcore.command.type.template.TemplateCommand;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TextCommand extends TemplateCommand {

    private String text;

    public TextCommand(String name, String[] activators, String description, String usage, int staffLevel, String text) {
        super(name, activators, description, usage, staffLevel);
        this.text = text;
    }

    public TextCommand(Map<String, String> map) {
        super(map.get("name"), map.get("activators").split(","), map.get("description"), map.get("usage"), Integer.parseInt(map.get("staffLevel")));
        this.text = map.get("text");
    }

    @Override
    public CommandResponse runTemplate(String[] args, MessageReceivedEvent event) {
        return new CommandResponse(text);
    }

    @Override
    public JSONObject getJSON() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", getKey());
        map.put("staffLevel", "" + getStaffLevel());
        map.put("description", getDescription());
        map.put("usage", getUsage());
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

        return new JSONObject(map);
    }
}
