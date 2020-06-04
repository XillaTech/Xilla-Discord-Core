package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.Log;
import com.tobiassteely.tobiasapi.api.manager.ManagerCache;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.type.basic.BasicCommand;
import net.xilla.discordcore.command.type.basic.BasicCommandExecutor;
import net.xilla.discordcore.command.type.legacy.LegacyCommand;
import net.xilla.discordcore.command.type.template.TemplateManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandManager extends ManagerParent {

    private ExecutorService executor;
    private CommandWorker commandWorker;

    private ConcurrentHashMap<String, Vector<BasicCommand>> basicCommandsByModule;
    private ConcurrentHashMap<String, BasicCommand> basicCommandsByName;
    private TemplateManager templateManager;

    public CommandManager() {
        super(false);
        addCache("activators", new ManagerCache());
        addCache("modules", new ManagerCache());
        this.executor = Executors.newFixedThreadPool(10);
        this.commandWorker = new CommandWorker();
        this.basicCommandsByModule = new ConcurrentHashMap<>();
        this.basicCommandsByName = new ConcurrentHashMap<>();
        this.templateManager = new TemplateManager();

        registerPingCommand();
        registerEndCommand();
    }

    private void registerPingCommand() {
        BasicCommandExecutor executor = (name, args, event) -> {
            if(event != null) {
                long start = System.currentTimeMillis();
                event.getTextChannel().sendMessage("Pong!").complete().editMessage("Pong! (" + (System.currentTimeMillis() - start) + "ms)").queue();

                return null;
            } else {
                return new CommandResponse("Pong!");
            }
        };
        BasicCommand basicCommand = new BasicCommand("Core", "Ping", "Returns with a pong!", 0, executor);

        registerBasicCommand(basicCommand);
    }

    private void registerEndCommand() {
        BasicCommandExecutor executor = (name, args, event) -> {
            if(event != null) {
                event.getTextChannel().sendMessage("Goodbye.").queue();
            }
            Log.sendMessage(0, "Goodbye.");
            System.exit(0);
            return null;
        };
        BasicCommand basicCommand = new BasicCommand("Core", "End", "Shutdown the bot!", 10, executor);

        registerBasicCommand(basicCommand);
    }

    private void registereeEndCommand() {

        BasicCommandExecutor executor = (name, args, event) -> {
            String title = args[0];
            StringBuilder message = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                message.append(args[i]);

                if(i != args.length - 1) {
                    message.append(args[i]).append(" ");
                }
            }

            return new CommandResponse(new EmbedBuilder().setTitle(title).setDescription(message.toString()));
        };
        BasicCommand basicCommand = new BasicCommand("Core", "Embed", "Embed a message!", 5, executor);

        registerBasicCommand(basicCommand);
    }

    public void createSimpleCommand(String command, String description, int staffLevel, String response) {
        BasicCommandExecutor executor = (name, args, event) -> new CommandResponse(response);
        BasicCommand basicCommand = new BasicCommand("Core", command, description, staffLevel, executor);

        registerBasicCommand(basicCommand);
    }

    public void createSimpleCommand(String command, String description, int staffLevel, EmbedBuilder response) {
        BasicCommandExecutor executor = (name, args, event) -> new CommandResponse(response);
        BasicCommand basicCommand = new BasicCommand("Core", command, description, staffLevel, executor);

        registerBasicCommand(basicCommand);
    }

    public void registerCommand(LegacyCommand command) {
        addObject(command);
        if(!getCache("modules").isCached(command.getModule()))
            getCache("modules").putObject(command.getModule(), new ArrayList<>());

        ArrayList<Object> commands = (ArrayList<Object>)getCache("modules").getObject(command.getModule());
        commands.add(command);

        for(String activator : command.getActivators())
            getCache("activators").putObject(activator.toLowerCase(), command);
    }

    public void registerBasicCommand(BasicCommand command) {
        if(!basicCommandsByModule.containsKey(command.getModule())) {
            basicCommandsByModule.put(command.getModule(), new Vector<>());
        }
        basicCommandsByModule.get(command.getModule()).add(command);

        if(!basicCommandsByName.containsKey(command.getName().toLowerCase())) {
            basicCommandsByName.put(command.getName().toLowerCase(), command);
        } else {
            Log.sendMessage(2, "Command (" + command.getName() + ") already exists!");
        }

    }

    public boolean runCommand(String input, MessageReceivedEvent event) {
        String commandInput = input.split(" ")[0].toLowerCase();
        String[] args = Arrays.copyOfRange(input.split(" "), 1, input.split(" ").length);

        if(runLegacyCommand(commandInput, args, event)) {
            return true;
        } else if(runBasicCommand(commandInput, args, event)){
            return true;
        }

        if(event == null) {
            Log.sendMessage(2, "Unknown command, type \"?\" for a list of available commands.");
            return false;
        }

        return false;
    }

    public boolean runGameCommand(String input) {
        String commandInput = input.split(" ")[0].toLowerCase();
        String[] args = Arrays.copyOfRange(input.split(" "), 1, input.split(" ").length);

        if(runLegacyCommand(commandInput, args, null)) {
            return true;
        } else if(runBasicCommand(commandInput, args, null)){
            return true;
        }

        runLegacyCommand("help", new String[] {}, null);
        return true;
    }

    public boolean runLegacyCommand(String commandInput, String[] args, MessageReceivedEvent event) {
        if(getCache("activators").isCached(commandInput)) {
            LegacyCommand command = (LegacyCommand)getCache("activators").getObject(commandInput);
            if(event != null) {
                if(!DiscordCore.getInstance().getPlatform().getStaffManager().hasPermission(event.getGuild(), event.getAuthor(), command.getStaffLevel())) {
                    return true;
                }
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
            return false;
        }
    }

    public boolean runBasicCommand(String commandInput, String[] args, MessageReceivedEvent event) {
        if(basicCommandsByName.containsKey(commandInput.toLowerCase())) {
            BasicCommand basicCommand = basicCommandsByName.get(commandInput.toLowerCase());
            if(event != null) {
                if(!DiscordCore.getInstance().getPlatform().getStaffManager().hasPermission(event.getGuild(), event.getAuthor(), basicCommand.getStaffLevel())) {
                    return true;
                }
            }
            Callable<Object> callableTask = () -> {
                basicCommand.run(event, args);
                return null;
            };
            executor.submit(callableTask);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<LegacyCommand> getCommandsByModule(String module) {
        if(getCache("modules").isCached(module))
            return (ArrayList<LegacyCommand>)getCache("modules").getObject(module);
        else return null;
    }

    public CommandWorker getCommandWorker() {
        return commandWorker;
    }

    public ConcurrentHashMap<String, BasicCommand> getBasicCommandsByName() {
        return basicCommandsByName;
    }

    public ConcurrentHashMap<String, Vector<BasicCommand>> getBasicCommandsByModule() {
        return basicCommandsByModule;
    }

    public TemplateManager getTemplateManager() {
        return templateManager;
    }
}
