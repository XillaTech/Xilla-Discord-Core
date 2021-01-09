package net.xilla.discordcore.core.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.command.response.CommandResponse;
import net.xilla.discordcore.core.permission.PermissionAPI;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Command extends ManagerObject {

    private String module;
    private String name;
    private String description;
    private List<CommandExecutor> executors;
    private String usage;
    private String permission;
    private String[] activators;
    private boolean consoleSupported;

    public Command(String module, String name, String[] activators, String usage, String description, Object permission, List<CommandExecutor> executors) {
        super(name, "Commands");
        this.module = module;
        this.name = name;
        this.description = description;
        this.executors = executors;
        this.activators = activators;

        if(permission != null) {
            this.permission = permission.toString();
        } else {
            this.permission = null;
        }

        this.usage = usage;
        this.consoleSupported = true;
    }

    public Command(String module, String name, String[] activators, String usage, String description, Object permission, List<CommandExecutor> executors, boolean consoleSupported) {
        super(name, "Commands");
        this.module = module;
        this.name = name;
        this.description = description;
        this.executors = executors;
        this.activators = activators;

        if(permission != null) {
            this.permission = permission.toString();
        } else {
            this.permission = null;
        }

        this.usage = usage;
        this.consoleSupported = consoleSupported;
    }

    public boolean isConsoleSupported() {
        return consoleSupported;
    }

    public ArrayList<CommandResponse> run(CommandData data) {
        ArrayList<CommandResponse> responses = new ArrayList<>();

        boolean hasPermission = true;
        if(permission != null && data.get() instanceof MessageReceivedEvent) {
            MessageReceivedEvent event = (MessageReceivedEvent) data.get();
            hasPermission = PermissionAPI.hasPermission(event.getMember(), permission);
        }

        if(hasPermission) {
            for (CommandExecutor executor : executors) {
                try {
                    CommandResponse response = executor.run(data);
                    if (response != null) {
                        responses.add(response);
                    }
                } catch (Exception ex) {
                    Logger.log(LogLevel.ERROR, "Error while running command: " + data.getCommand(), getClass());
                    ex.printStackTrace();

                    StringBuilder builder = new StringBuilder();
                    builder.append("Error `").append(ex.getMessage()).append("`\n");
                    for(StackTraceElement element : ex.getStackTrace()) {
                        builder.append("> ").append(element.toString()).append("\n");
                    }

                    responses.add(new CommandResponse(data).setTitle("Error while running command " + data.getCommand() + "!").setDescription(builder.toString()));
                }
            }
        } else {
            responses.add(DiscordCore.getInstance().getCommandManager().getPermissionError().getResponse(data.getArgs(), data));
        }
        return responses;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getModule() {
        return module;
    }

    public String[] getActivators() {
        return activators;
    }

    public String getUsage() {
        return usage;
    }

    public List<CommandExecutor> getExecutors() {
        return executors;
    }

    public Command setExecutors(List<CommandExecutor> executors) {
        this.executors = executors;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActivators(String[] activators) {
        this.activators = activators;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public XillaJson getSerializedData() {
        JSONObject map = new JSONObject();
        map.put("name", getKey());
        map.put("module", getModule());
        map.put("description", getDescription());
        map.put("usage", getUsage());
        map.put("permission", getPermission());
        StringBuilder activatorsParsed = new StringBuilder();
        for(int i = 0; i < getActivators().length; i++) {
            activatorsParsed.append(getActivators()[i]);
            if(i != getActivators().length - 1) {
                activatorsParsed.append(",");
            }
        }
        map.put("activators", activatorsParsed.toString());


        return new XillaJson(new JSONObject(map));
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }
}
