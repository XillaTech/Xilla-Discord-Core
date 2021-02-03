package net.xilla.discordcore.startup;

import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;

import java.util.ArrayList;
import java.util.Vector;

public class PostStartupManager {

    private Vector<PostStartupExecutor> executors;

    public PostStartupManager() {
        this.executors = new Vector<>();
    }

    public void addExecutor(PostStartupExecutor executor) {
        executors.add(executor);
    }

    public void run() {
        for(PostStartupExecutor executor : new ArrayList<>(executors)) {
            Logger.log(LogLevel.DEBUG, "Starting executor " + executor, getClass());
            executor.run();
        }
    }

}
