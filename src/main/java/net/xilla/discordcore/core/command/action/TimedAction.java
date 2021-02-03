package net.xilla.discordcore.core.command.action;

import net.dv8tion.jda.api.entities.Message;

import java.util.Timer;
import java.util.TimerTask;

public class TimedAction implements PostCommandAction {

    private PostCommandAction action;
    private long delay;
    private Timer t = new Timer();

    public TimedAction(PostCommandAction action, long delay) {
        this.action = action;
        this.delay = delay;
    }

    @Override
    public void run(Message message) {
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                message.delete().queue();
            }
        }, 10000);
    }

}
