package net.xilla.discordcore.commandsystem;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.api.manager.ManagerParent;

import java.util.ArrayList;

public class MessageEventManger extends ManagerParent {

    private ArrayList<MessageEventHandler> events;

    public MessageEventManger() {
        this.events = new ArrayList<>();
    }

    public void registerMessageEvent(MessageEventHandler messageEventHandler) {
        events.add(messageEventHandler);
    }

    public void runEvent(MessageReceivedEvent messageReceivedEvent) {
        for(MessageEventHandler messageEventHandler : events) {
            messageEventHandler.messageEventHandler(messageReceivedEvent);
        }
    }

}
