package net.xilla.discordcore.command.type.template;

import com.tobiassteely.tobiasapi.api.Log;
import com.tobiassteely.tobiasapi.api.config.Config;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.type.template.type.EmbedCommand;
import net.xilla.discordcore.command.type.template.type.TextCommand;
import org.json.simple.JSONObject;

public class TemplateManager extends ManagerParent {

    public TemplateManager() {
        super(false);
    }

    public void registerTemplate(TemplateCommand templateCommand) {
        addObject(templateCommand);
        DiscordCore.getInstance().getPlatform().getCommandManager().registerCommand(templateCommand);
    }

    public void reload() {
        super.reload();

        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("commands.json");
        JSONObject json = config.toJson();
        for(Object key : json.keySet()) {
            String type = config.getMap((String)key).get("type");
            TemplateCommand command = null;
            if(type.equalsIgnoreCase("Embed")) {
                command = new EmbedCommand(config.getMap((String)key));
            } else if(type.equalsIgnoreCase("Text")) {
                command = new TextCommand(config.getMap((String)key));
            }

            if(command != null) {
                registerTemplate(command);
            }
        }
    }

    public void save() {
        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("commands.json");
        for(ManagerObject object : getList()) {
            TemplateCommand templateCommand = (TemplateCommand) object;
            config.toJson().put(templateCommand.getKey(), templateCommand.getJSON());
        }
        config.save();
    }

}
