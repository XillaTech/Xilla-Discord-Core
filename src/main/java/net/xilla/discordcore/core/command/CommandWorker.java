package net.xilla.discordcore.core.command;


import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandWorker extends Thread {

    private boolean isActive = true;
    private String welcome;

    public CommandWorker(String welcome) {
        this.welcome = welcome;
    }

    @Override
    public void run() {

        Logger.log(LogLevel.INFO, "-----------------------------------------", getClass());
        Logger.log(LogLevel.INFO, welcome, getClass());
        Logger.log(LogLevel.INFO, "-----------------------------------------", getClass());

        CommandManager commandManager = DiscordCore.getInstance().getCommandManager();
        Logger.log(LogLevel.INFO, "Type \"?\" to view possible commands", getClass());
        while(true) {
            if(!isActive)
                break;

            Scanner scanner = new Scanner(System.in);
            try {
                String command = scanner.nextLine();
                if(command != null)
                    commandManager.runRawCommandInput(command, CommandData.command_line_input, new ConsoleUser());
            } catch (NoSuchElementException ignored) {}
        }
        System.exit(0);
    }

    @Override
    public void interrupt() {
        Logger.log(LogLevel.INFO, "Attempting soft shutdown.", getClass());
        isActive = false;
        try {
            Thread.sleep(10000);
            Logger.log(LogLevel.WARN, "Frozen for 10 seconds, forcing shutdown.", getClass());
            super.interrupt();
            System.exit(0);

        } catch (InterruptedException ignore) {}
    }

}
