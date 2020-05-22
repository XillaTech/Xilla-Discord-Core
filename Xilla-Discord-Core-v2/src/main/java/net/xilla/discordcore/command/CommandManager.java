package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.Log;
import com.tobiassteely.tobiasapi.api.manager.ManagerCache;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandManager extends ManagerParent {

    private ExecutorService executor;
    private CommandWorker commandWorker;

    public CommandManager() {
        addCache("activators", new ManagerCache());
        addCache("modules", new ManagerCache());
        this.executor = Executors.newFixedThreadPool(10);
        this.commandWorker = new CommandWorker();
    }

    public void registerCommand(Command command) {
        addObject(command);
        if(!getCache("modules").isCached(command.getModule()))
            getCache("modules").putObject(command.getModule(), new ArrayList<>());

        ArrayList<Object> commands = (ArrayList<Object>)getCache("modules").getObject(command.getModule());
        commands.add(command);

        for(String activator : command.getActivators())
            getCache("activators").putObject(activator.toLowerCase(), command);
    }

    public boolean runCommand(String input, MessageReceivedEvent event) {
        String commandInput = input.split(" ")[0].toLowerCase();
        String[] args = Arrays.copyOfRange(input.split(" "), 1, input.split(" ").length);

        if(getCache("activators").isCached(commandInput)) {
            Command command = (Command)getCache("activators").getObject(commandInput);
            if(event != null) {
//                if(!DiscordCore.getInstance().getStaffManager().isAuthorized(event.getAuthor().getId(), command.getStaffLevel())) {
//                    return true;
//                }
                // MUST FIX BEFORE PUBLIC RELEASE / TESTING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }
            Callable<Object> callableTask = () -> {
                if(!command.run(args, event)) {
                    if(event == null) {
                        Log.sendMessage(2, "Command registered, but not running properly.");
                    }
                }
                return null;
            };
            executor.submit(callableTask);
            return true;
        } else {
            if(event == null) {
                Log.sendMessage(2, "Unknown command, type \"?\" for a list of available commands.");
                return false;
            }
            return true;
        }
    }

    public ArrayList<Command> getCommandsByModule(String module) {
        if(getCache("modules").isCached(module))
            return (ArrayList<Command>)getCache("modules").getObject(module);
        else return null;
    }

    public CommandWorker getCommandWorker() {
        return commandWorker;
    }
}
