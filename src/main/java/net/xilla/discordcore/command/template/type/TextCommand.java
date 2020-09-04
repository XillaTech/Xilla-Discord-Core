package net.xilla.discordcore.command.template.type;

import com.tobiassteely.tobiasapi.command.response.CommandResponse;
import net.xilla.discordcore.command.template.TemplateCommand;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TextCommand extends TemplateCommand {

    private String text;

    public TextCommand(String module, String name, String[] activators, String description, String usage, String text, String permission) {
        super(module, name, activators, description, usage, permission, (data) -> new CommandResponse(data).setDescription(text));
        this.text = text;
    }

    public TextCommand(Map<String, String> map) {
        super(
                map.get("module"),
                map.get("name"),
                map.get("activators").split(","),
                map.get("description"), map.get("usage"),
                map.get("permission"),
                (data) -> new CommandResponse(data).setDescription(map.get("text"))
        );
        this.text = map.get("text");
    }

    @Override
    public JSONObject getJSON() {
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

        return new JSONObject(map);
    }
}
