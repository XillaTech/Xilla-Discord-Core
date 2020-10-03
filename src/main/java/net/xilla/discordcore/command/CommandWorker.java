package net.xilla.discordcore.command;

import net.xilla.core.library.worker.Worker;

public class CommandWorker extends Worker {

    public CommandWorker() {
        super("Commands", 1000);
    }

    public void runWorker(long start) {
        CommandEventHandler.processCache();
    }

}
