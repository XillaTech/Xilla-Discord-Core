package net.xilla.discord.api.command;

import net.xilla.core.library.Nullable;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discord.api.Processor;
import net.xilla.discord.api.permission.PermissionUser;

/**
 * Processes and executes all discord commands
 */
public interface CommandProcessor extends Processor<String, Command> {

    default boolean processSafe(CommandInput input) {
        try {
            Command command = null;
            for(Command temp : listObjects()) {
                for(String activator : temp.getActivators()) {
                    if(activator.equalsIgnoreCase(input.getCommand())) {
                        command = temp;
                        break;
                    }
                }
                if(command != null) {
                    break;
                }
            }
            if(command == null) {
                return false;
            }

            return processCommand(command, input);
        } catch (Exception ex) {
            Logger.log(LogLevel.ERROR, "Failed to run command " + input.getCommand(), getClass());
            Logger.log(ex, getClass());
            return false;
        }
    }

    /**
     * Attempts to process an unknown command input. First
     * it tries to grab the command, if it is null it will
     * return false. Second it will check the permission user
     * associated with the command input to make sure they
     * have access to the command. If they do not, it will
     * return false. If everything works successfully the
     * function returns true.
     *
     * @param input Command Input
     * @return Command Execution Status
     */
    default boolean processRaw(CommandInput input) throws Exception {
        Command command = get(input.getCommand());
        if(command == null) {
            return false;
        }

        return processCommand(command, input);
    }


    /**
     * Attempts to process a known command input. It
     * will check the permission user associated with
     * the command input to make sure they have ccess
     * to the command. If they do not it will return
     * false, otherwise the program returns true as
     * the commands executor was ran.
     * @param command Command
     * @param input Command Input
     * @return Command Succeeded
     * @throws Exception Command Exception
     */
    default boolean processCommand(Command command, CommandInput input) throws Exception {
        String permission = command.getPermission();
        if(permission != null) {
            PermissionUser user = input.getExecutor();

            if (!user.hasPermission(command.getPermission())) {
                return false;
            }
        }

        command.getExecutor().run(input);
        return true;
    }

}
