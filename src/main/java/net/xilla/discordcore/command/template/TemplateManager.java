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
        super("Templates", "commands.json");
        load();
    }

    public void registerTemplate(TemplateCommand templateCommand) {
        put(templateCommand);
        DiscordCore.getInstance().getCommandManager().put(templateCommand);
    }

    @Override
    protected void load() {
        for(Object key : getConfig().getJson().getJson().keySet()) {
            String type = getConfig().getMap((String)key).get("type");
            TemplateCommand command;
            if(type.equalsIgnoreCase("embed")) {
                command = new EmbedCommand(getConfig().getJSON((String) key));
            } else {
                command = new TextCommand(getConfig().getJSON((String) key));
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
