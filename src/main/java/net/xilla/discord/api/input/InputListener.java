package net.xilla.discord.api.input;

import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.manager.ObjectInterface;

/**
 * Base for the user input listeners, used to
 * collect data from a discord user.
 */
public interface InputListener extends ObjectInterface {

    User getUser();

    long getStartTime();

    long getWaitTime();

    void setStartTime(long time);

    InputProcessor getProcessor();

    default void start() {

    }

    default void stop(boolean wasSuccessful) {

    }

}
