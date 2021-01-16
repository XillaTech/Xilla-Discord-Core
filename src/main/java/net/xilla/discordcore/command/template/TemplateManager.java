package net.xilla.discordcore.command.template;

import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.template.type.EmbedCommand;
import net.xilla.discordcore.command.template.type.ScriptCommand;
import net.xilla.discordcore.command.template.type.TextCommand;

import java.util.List;

public class TemplateManager extends Manager<String, TemplateCommand> {

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
            if(key.toString().equalsIgnoreCase("file-extension")) {
                continue;
            }
            System.out.println("" + getConfig().get(key.toString()));
            String type = getConfig().getMap(key.toString()).get("type");
            TemplateCommand command = null;
            if(type.equalsIgnoreCase("embed")) {
                command = new EmbedCommand(getConfig().getJSON((String) key));
            } else if(type.equalsIgnoreCase("text")) {
                command = new TextCommand(getConfig().getJSON((String) key));
            } else if(type.equalsIgnoreCase("script")) {
                command = new ScriptCommand(getConfig().getJSON((String) key));
            }

            if(command != null) {
                registerTemplate(command);
            }
        }
    }

    @Override
    protected void objectAdded(TemplateCommand templateCommand) {

    }

    @Override
    protected void objectRemoved(TemplateCommand templateCommand) {

    }

    public List<TemplateCommand> getCommands() {
        return iterate();
    }

    public TemplateCommand getTemplateCommand(String name) {
        return get(name);
    }

    public void removeTemplateCommand(String name) {
        remove(name);
    }

}
