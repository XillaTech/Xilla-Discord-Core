package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Command extends ManagerObject {

    private String[] activators;
    private String module;
    private String description;
    private String usage;
    private int staffLevel;

    public Command(String name, String[] activators, String module, String description, String usage, int staffLevel) {
        super(name);
        this.activators = activators;
        this.module = module;
        this.description = description;
        this.usage = usage;
        this.staffLevel = staffLevel;
    }

    public String getName() {
        return getKey();
    }

    public String getDescription() {
        return description;
    }

    public String[] getActivators() {
        return activators;
    }

    public String getModule() {
        return module;
    }

    public int getStaffLevel() {
        return staffLevel;
    }

    public String getUsage() {
        return usage;
    }

    public void setActivators(String[] activators) {
        this.activators = activators;
    }

    public boolean run(String[] args, MessageReceivedEvent event) {
        return false; // OVERRIDE
    }
}
