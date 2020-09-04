package net.xilla.discordcore.command.template;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.api.manager.ManagerCache;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.config.Config;
import net.xilla.discordcore.command.template.type.EmbedCommand;
import net.xilla.discordcore.command.template.type.TextCommand;
import org.json.simple.JSONObject;

public class TemplateManager extends ManagerParent<TemplateCommand> {

    public TemplateManager() {
        super("XDC.Template", false);
    }

    public void registerTemplate(TemplateCommand templateCommand) {
        addObject(templateCommand);
        TobiasAPI.getInstance().getCommandManager().addObject(templateCommand);
    }

    public void reload() {
        super.reload();

        Config config = TobiasAPI.getInstance().getConfigManager().getConfig("commands.json");
        JSONObject json = config.toJson();
        for(Object key : json.keySet()) {
            String type = config.getMap((String)key).get("type");
            TemplateCommand command;
            if(type.equalsIgnoreCase("embed")) {
                command = new EmbedCommand(config.getMap((String) key));
            } else {
                command = new TextCommand(config.getMap((String) key));
            }
            registerTemplate(command);
        }
    }

    public void save() {
        Config config = TobiasAPI.getInstance().getConfigManager().getConfig("commands.json");
        config.clear();
        for(TemplateCommand templateCommand : getList()) {
            config.toJson().put(templateCommand.getKey(), templateCommand.getJSON());
        }
        config.save();
    }

    public ManagerCache getCommands() {
        return getCache("key");
    }

    public TemplateCommand getTemplateCommand(String name) {
        return (TemplateCommand)getCommands().getObject(name);
    }

    public void removeTemplateCommand(String name) {
        removeObject(name);
    }

}
