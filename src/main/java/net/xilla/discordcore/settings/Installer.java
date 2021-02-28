package net.xilla.discordcore.settings;

import net.xilla.core.library.config.Config;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.library.CoreObject;
import net.xilla.discordcore.ui.MenuManager;

import java.util.Scanner;

public class Installer implements CoreObject {

    private Config config;
    private Scanner scanner;
    private String input = null;

    public Installer(Config config) {
        this.config = config;
        this.scanner = new Scanner(System.in);
    }

    public String waitOnInput() {
        if(MenuManager.getInstance() != null) {
            Thread threadOne = new Thread(() -> {
                input = scanner.nextLine();
            });
            Thread threadTwo = new Thread(() -> {
                input = MenuManager.getInstance().getConsolePane().waitOnNextCommand();
            });

            threadOne.start();
            threadTwo.start();

            String temp = "";
            while (input == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) { }
            }

            temp = input;
            input = null;
            return temp;
        } else {
            return scanner.nextLine();
        }
    }

    public void install(String desc, String key, String def) {
        config.setDefault(key, def);
        if(config.getString(key).equals(def)) {
            Logger.log(LogLevel.INFO, "", getClass());
            Logger.log(LogLevel.INFO, "Please input a string for the config (" + config.getKey() + ") option: " + key, getClass());
            Logger.log(LogLevel.INFO, " > " + desc, getClass());

            config.set(key, waitOnInput());
            config.save();
        }
    }

    public void install(String desc, String key, int def) {
        config.setDefault(key, def);
        if(config.getInt(key) == def) {
            Logger.log(LogLevel.INFO, "", getClass());
            Logger.log(LogLevel.INFO, "Please input an integer (#) for the config (" + config.getKey() + ") option: " + key, getClass());
            Logger.log(LogLevel.INFO, " > " + desc, getClass());

            config.set(key, waitOnInput());
            config.save();
        }
    }

    public void install(String desc, String key, double def) {
        config.setDefault(key, def);
        if(config.getInt(key) == def) {
            Logger.log(LogLevel.INFO, "", getClass());
            Logger.log(LogLevel.INFO, "Please input a double (#.#) for the config (" + config.getKey() + ") option: " + key, getClass());
            Logger.log(LogLevel.INFO," > " + desc, getClass());

            config.set(key, waitOnInput());
            config.save();
        }
    }
}
