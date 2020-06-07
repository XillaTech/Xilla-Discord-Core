package net.xilla.discordcore.settings;

import com.tobiassteely.tobiasapi.config.Config;

import java.util.Scanner;

public class Installer {

    private Config config;
    private Scanner scanner;

    public Installer(Config config) {
        this.config = config;
        this.scanner = new Scanner(System.in);
    }

    public void install(String desc, String key, String def) {
        config.loadDefault(key, def);
        if(config.getString(key).equals(def)) {
            System.out.println();
            System.out.println("Please input a string for the config (" + config.getFile() + ") option: " + key);
            System.out.println(" > " + desc);

            config.set(key, scanner.nextLine());
            config.save();
        }
    }

    public void install(String desc, String key, int def) {
        config.loadDefault(key, def);
        if(config.getInt(key) == def) {
            System.out.println();
            System.out.println("Please input an integer (#) for the config (" + config.getFile() + ") option: " + key);
            System.out.println(" > " + desc);

            config.set(key, scanner.nextInt());
            config.save();
        }
    }

    public void install(String desc, String key, double def) {
        config.loadDefault(key, def);
        if(config.getInt(key) == def) {
            System.out.println();
            System.out.println("Please input a double (#.#) for the config (" + config.getFile() + ") option: " + key);
            System.out.println(" > " + desc);

            config.set(key, scanner.nextInt());
            config.save();
        }
    }
}
