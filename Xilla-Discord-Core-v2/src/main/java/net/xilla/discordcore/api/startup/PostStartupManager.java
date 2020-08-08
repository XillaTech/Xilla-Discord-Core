package net.xilla.discordcore.api.startup;

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
        for(PostStartupExecutor executor : executors) {
            executor.run();
        }
    }

}
