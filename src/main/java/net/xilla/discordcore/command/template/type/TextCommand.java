package net.xilla.discordcore.command.template.type;

import net.xilla.core.library.json.XillaJson;
import net.xilla.discordcore.command.template.TemplateCommand;
import net.xilla.discordcore.core.command.response.CommandResponse;
import org.json.simple.JSONObject;

public class TextCommand extends TemplateCommand {

    private String text;

    public TextCommand(String module, String name, String[] activators, String description, String usage, String text, String permission) {
        super(module, name, activators, description, usage, permission, (data) -> new CommandResponse(data).setDescription(text));
        this.text = text;
    }

    public TextCommand(JSONObject map) {
        super(
                map.get("module").toString(),
                map.get("name").toString(),
                map.get("activators").toString().split(","),
                map.get("description").toString(), map.get("usage").toString(),
                map.get("permission"),
                (data) -> new CommandResponse(data).setDescription(map.get("text").toString())
        );
        this.text = map.get("text").toString();
    }

    @Override
    public XillaJson getSerializedData() {
        JSONObject map = new JSONObject();
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
    public void setData(String text) {
        this.text = text;
    }

    @Override
    public String getData() {
        return text;
    }

}
