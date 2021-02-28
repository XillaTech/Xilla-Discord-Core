package net.xilla.discordcore.library;

import lombok.Getter;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/***
 * Allows you to run a batch of RestActions in order using the queue system
 */
public class QueueHandler <T> {

    @Getter
    private Collection<RestAction<T>> actions = new Vector<>();

    private AtomicBoolean waiting = new AtomicBoolean(false);

    private List<T> results = new ArrayList<>();

    private Consumer<? super Throwable> throwable;

    private Consumer<? super T> action;

    public QueueHandler() {
        this(throwable -> {});
    }

    public QueueHandler(Consumer<? super Throwable> throwable) {
        this(obj -> {}, throwable);
    }

    public QueueHandler(Consumer<? super T> action, Consumer<? super Throwable> throwable) {
        this.action = action;
        this.throwable = throwable;
    }

    public void addRestAction(RestAction<T> action) {
        actions.add(action);
    }

    public List<T> startAndWait() {
        start();
        while (waiting.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
        return results;
    }

    public void start() {
        waiting.set(true);
        RestAction<List<T>> action = RestAction.allOf(actions);

        Consumer finished = (o -> waiting.set(false));
        Consumer<List<T>> saveResults = (o) -> results.addAll(o);

        action.queue(saveResults.andThen(finished), throwable.andThen(finished));
    }

}

