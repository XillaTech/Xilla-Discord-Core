package net.xilla.discordcore.command.template;

import net.xilla.core.library.config.Config;
import net.xilla.core.library.config.ConfigManager;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.manager.ManagerCache;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.template.type.EmbedCommand;
import net.xilla.discordcore.command.template.type.TextCommand;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class TemplateManager extends Manager<TemplateCommand> {

    public TemplateManager() {
        super("Templates");
    }

    public void registerTemplate(TemplateCommand templateCommand) {
        put(templateCommand);
        DiscordCore.getInstance().getCommandManager().put(templateCommand);
    }

    public void reload() {
    }

    public void save() {
        Config config = ConfigManager.getInstance().get("commands.json");
        config.clear();
        for(TemplateCommand templateCommand : new ArrayList<>(getData().values())) {
            config.set(templateCommand.getKey(), templateCommand.getJSON());
        }
        config.save();
    }

    @Override
    protected void load() {
        Config config = ConfigManager.getInstance().get("commands.json");
        JSONObject json = config.getJson().getJson();
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

    @Override
    protected void objectAdded(TemplateCommand templateCommand) {

    }

    @Override
    protected void objectRemoved(TemplateCommand templateCommand) {

    }

    public ManagerCache getCommands() {
        return getCache("key");
    }

    public TemplateCommand getTemplateCommand(String name) {
        return (TemplateCommand)getCommands().getObject(name);
    }

    public void removeTemplateCommand(String name) {
        remove(name);
    }

}
