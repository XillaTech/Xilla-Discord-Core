package net.xilla.discordcore.commandsystem;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.api.Log;
import net.xilla.discordcore.api.manager.ManagerCache;
import net.xilla.discordcore.api.manager.ManagerParent;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager extends ManagerParent {

    private static CommandManager instance;

    public static CommandManager getInstance() {
        return instance;
    }

    public CommandManager() {
        addCache("activators", new ManagerCache());
        addCache("modules", new ManagerCache());
        instance = this;
    }

    public void registerCommand(CommandObject commandObject) {
        addObject(commandObject);
        if(!getCache("modules").isCached(commandObject.getModule()))
            getCache("modules").putObject(commandObject.getModule(), new ArrayList<>());

        ArrayList<Object> commands = (ArrayList<Object>)getCache("modules").getObject(commandObject.getModule());
        commands.add(commandObject);

        for(String activator : commandObject.getActivators())
            getCache("activators").putObject(activator.toLowerCase(), commandObject);
    }

    public void runCommand(String input, MessageReceivedEvent event) {
        String command = input.split(" ")[0].toLowerCase();
        String[] args = Arrays.copyOfRange(input.split(" "), 1, input.split(" ").length);

        if(getCache("activators").isCached(command)) {
            CommandObject commandObject = (CommandObject)getCache("activators").getObject(command);
            if(!commandObject.run(args, event)) {
                if(event == null)
                    Log.sendMessage(2, "Command registered, but not running properly.");
            }
        } else if(event == null) {
            Log.sendMessage(2, "Unknown command, type \"?\" for a list of available commands.");
        }
    }

    public ArrayList<CommandObject> getCommandsByModule(String module) {
        if(getCache("modules").isCached(module))
            return (ArrayList<CommandObject>)getCache("modules").getObject(module);
        else return null;
    }

}
