package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.Log;
import net.xilla.discordcore.DiscordCore;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandWorker extends Thread {

    private boolean isActive = true;

    @Override
    public void run() {

        Log.sendMessage(0, "-----------------------------------------");
        Log.sendMessage(0, "Xilla Discord Core - 2.0.0");
        Log.sendMessage(0, "-----------------------------------------");

        CommandManager commandManager = DiscordCore.getInstance().getPlatform().getCommandManager();
        Log.sendMessage(0, "Type \"?\" to view possible commands");
        while(true) {
            if(!isActive)
                break;

            Scanner scanner = new Scanner(System.in);
            try {
                String command = scanner.nextLine();
                if(command != null)
                    commandManager.runCommand(command, null);
            } catch (NoSuchElementException ignored) {}
        }
        System.exit(0);
    }

    @Override
    public void interrupt() {
        Log.sendMessage(0, "Attempting soft shutdown.");
        isActive = false;
        try {
            Thread.sleep(10000);
            Log.sendMessage(1, "Frozen for 10 seconds, forcing shutdown.");
            super.interrupt();
            System.exit(0);

        } catch (InterruptedException ignore) {}
    }

}
