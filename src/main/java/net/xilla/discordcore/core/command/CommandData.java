package net.xilla.discordcore.core.command;

import net.xilla.discordcore.core.command.flag.CommandFlag;
import net.xilla.discordcore.core.command.permission.user.PermissionUser;

import java.util.HashMap;
import java.util.Map;

public class CommandData<T> {

    private String command;
    private String[] args;
    private Map<CommandFlag, String> flags;
    private T data;
    private String inputType;
    private PermissionUser user;

    public static final String command_line_input = "commandline";

    public CommandData(String command, String[] args, T data, String inputType, PermissionUser user) {
        this.command = command;
        this.args = args;
        this.data = data;
        this.inputType = inputType;
        this.user = user;
        this.flags = new HashMap<>();
    }

    public CommandData(String command, String[] args, T data, String inputType, PermissionUser user, Map<CommandFlag, String> flags) {
        this.command = command;
        this.args = args;
        this.data = data;
        this.inputType = inputType;
        this.user = user;
        this.flags = flags;
    }

    public Map<CommandFlag, String> getFlags() {
        return flags;
    }

    public T get() {
        return data;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public PermissionUser getUser() {
        return user;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }
}
