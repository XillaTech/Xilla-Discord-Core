package net.xilla.discord.api.command;

/**
 * Executor used for commands to actually be processed.
 */
public interface CommandExecutor {

    /**
     * The method that contains all the code inside of a command.
     *
     * @param input Command Input
     * @throws Exception Possible Exceptions
     */
    void run(CommandInput input) throws Exception;

}
