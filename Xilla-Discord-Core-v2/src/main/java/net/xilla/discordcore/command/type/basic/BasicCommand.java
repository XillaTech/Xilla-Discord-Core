package net.xilla.discordcore.command.type.basic;

import com.tobiassteely.tobiasapi.api.Log;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.CommandResponse;

public class BasicCommand {

    private String module;
    private String name;
    private String description;
    private int staffLevel;
    private BasicCommandExecutor executor;

    public BasicCommand(String module, String name, String description, int staffLevel, BasicCommandExecutor executor) {
        this.module = module;
        this.name = name;
        this.description = description;
        this.staffLevel = staffLevel;
        this.executor = executor;
    }

    public void run(MessageReceivedEvent event, String[] args) {
        CommandResponse response = executor.run(name, args, event);
        Log.sendMessage(0, "AA");
        if(response != null) {
            if (event == null) {
                response.send();
            } else {
                response.send(event.getTextChannel());
            }
        }
    }

    public CommandResponse runWithResponse(MessageReceivedEvent event, String[] args) {
        CommandResponse response = executor.run(name, args, event);
        return response;
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

    public int getStaffLevel() {
        return staffLevel;
    }
}
