package net.xilla.discordcore.core.command.action;

import net.dv8tion.jda.api.entities.Message;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Actions extends Vector<PostCommandAction> implements PostCommandAction {

    public Actions(List<PostCommandAction> action) {
        addAll(action);
    }

    public Actions(PostCommandAction... action) {
        addAll(Arrays.asList(action));
    }

    @Override
    public void run(Message message) {
        forEach((a) -> a.run(message));
    }

}
