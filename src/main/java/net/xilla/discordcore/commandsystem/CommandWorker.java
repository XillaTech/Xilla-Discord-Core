package net.xilla.discordcore.commandsystem;

import com.fasterxml.jackson.databind.cfg.ConfigOverride;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.Data;
import net.xilla.discordcore.api.Log;
import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.api.config.ConfigManager;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandWorker extends Thread {

    private boolean isActive = true;

    @Override
    public void run() {

        Log.sendMessage(0, new Data().getLineBreak());
        Log.sendMessage(0, new Data().getVersion());
        Log.sendMessage(0, new Data().getLineBreak());

        CommandManager commandManager = CommandManager.getInstance();
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
