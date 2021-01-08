package net.xilla.discordcore.startup;

import net.dv8tion.jda.api.JDABuilder;

import java.util.ArrayList;
import java.util.Vector;

public class StartupManager {

    private Vector<StartupExecutor> executors;

    public StartupManager() {
        this.executors = new Vector<>();
    }

    public void addExecutor(StartupExecutor executor) {
        executors.add(executor);
    }

    public void run(JDABuilder builder) {
        for(StartupExecutor executor : new ArrayList<>(executors)) {
            executor.run(builder);
        }
    }

}
