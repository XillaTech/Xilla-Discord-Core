package net.xilla.discordcore.core.command.response;

import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.library.CoreObject;

public class BaseCommandResponder implements CoreObject, CommandResponder {

    public void send(String title, String text, String inputType) {
        Logger.log(LogLevel.INFO, title + "\n" + text, getClass());
    }

    public void send(String text, String inputType) {
        Logger.log(LogLevel.INFO, text, getClass());
    }

    @Override
    public void send(CommandResponse response) {
        if(response.getTitle() != null) {
            send(response.getTitle(), response.getDescription(), response.getInputType());
        } else {
            send(response.getDescription(), response.getInputType());
        }
    }
}
