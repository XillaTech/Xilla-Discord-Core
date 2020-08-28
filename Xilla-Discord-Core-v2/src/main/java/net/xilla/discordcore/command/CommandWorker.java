package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.worker.Worker;

public class CommandWorker extends Worker {

    public CommandWorker() {
        super("XDC.Command", 1000);
    }

    public Boolean runWorker(long start) {
        CommandEventHandler.processCache();
        return true;
    }

}
