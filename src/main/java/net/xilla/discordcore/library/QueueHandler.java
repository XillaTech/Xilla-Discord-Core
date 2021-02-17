package net.xilla.discordcore.library;

import lombok.Getter;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

/***
 * Allows you to run a batch of RestActions in order using the queue system
 */
public class QueueHandler {

    @Getter
    private List<RestAction> actions = new Vector<>();

    public void addRestAction(RestAction action) {
        actions.add(action);
    }

    public void startAndWait() {
        start();
        while (actions.size() != 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
    }

    public void start() {
        if(actions.size() > 0) {
            RestAction action = actions.remove(0);

            Consumer runNext = o -> start();
            action.queue(runNext, runNext);
        }
    }

}
