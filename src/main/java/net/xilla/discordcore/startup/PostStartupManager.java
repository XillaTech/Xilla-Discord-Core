package net.xilla.discordcore.startup;

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
            executor.run();
        }
    }

}
