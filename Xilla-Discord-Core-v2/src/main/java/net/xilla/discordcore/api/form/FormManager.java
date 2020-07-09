package net.xilla.discordcore.api.form;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.xilla.discordcore.CoreObject;

import java.util.*;

public class FormManager extends CoreObject {

    private ArrayList<FormReactionEvent> reactionEvents;
    private ArrayList<FormMessageEvent> messageEvents;
    private HashMap<String, Form> formsByMessageID;
    private HashMap<String, ArrayList<Form>> formsByUserID;

    public FormManager() {
        this.reactionEvents = new ArrayList<>();
        this.messageEvents = new ArrayList<>();
        this.formsByMessageID = new HashMap<>();
        this.formsByUserID = new HashMap<>();
    }

    public void addForm(Form form) {
        formsByMessageID.put(form.getMessage().getId(), form);
        if(!formsByUserID.containsKey(form.getFormOwner())) {
            formsByUserID.put(form.getFormOwner(), new ArrayList<>());
        }
        formsByUserID.get(form.getFormOwner()).add(form);
    }

    public Form getForm(String messageID) {
        return formsByMessageID.get(messageID);
    }

    public void removeForm(Form form) {
        formsByMessageID.remove(form.getMessage().getId());
    }

    public void addReactionEventHandler(FormReactionEvent event) {
        reactionEvents.add(event);
    }

    public void removeReactionEventHandler(FormReactionEvent event) {
        reactionEvents.remove(event);
    }

    public void addMessageEventHandler(FormMessageEvent event) {
        messageEvents.add(event);
    }

    public void removeMessageEventHandler(FormMessageEvent event) {
        messageEvents.remove(event);
    }

    public void runEvent(GuildMessageReactionAddEvent event) {
        Form form = formsByMessageID.get(event.getMessageId());
        boolean ran = false;
        if(form != null) {
            if(form.getReactionEvent() == null) {
                for (FormReactionEvent formReactionEvent : reactionEvents) {
                    if(formReactionEvent.runEvent(form, event))
                        ran = true;
                }
            } else {
                if(form.getReactionEvent().runEvent(form, event))
                    ran = true;
            }
            if(ran) {
                formsByMessageID.remove(event.getMessageId());
                formsByUserID.get(form.getFormOwner()).remove(form);
                if (formsByUserID.get(form.getFormOwner()).size() == 0) {
                    formsByUserID.remove(form.getFormOwner());
                }
            }
        }
    }

    public void runEvent(GuildMessageReceivedEvent event, Form form) {
        boolean ran = false;
        if (form.getMessageEvent() == null) {
            for (FormMessageEvent formMessageEvent : messageEvents) {
                if (formMessageEvent.runEvent(form, event))
                    ran = true;
            }
        } else {
            if (form.getMessageEvent().runEvent(form, event))
                ran = true;
        }
        if (ran) {
            formsByMessageID.remove(event.getMessageId());
            formsByUserID.get(form.getFormOwner()).remove(form);
            if (formsByUserID.get(form.getFormOwner()).size() == 0) {
                formsByUserID.remove(form.getFormOwner());
            }
        }
    }

    public ArrayList<FormReactionEvent> getReactionEvents() {
        return reactionEvents;
    }

    public HashMap<String, Form> getFormsByMessageID() {
        return formsByMessageID;
    }

    public HashMap<String, ArrayList<Form>> getFormsByUserID() {
        return formsByUserID;
    }

    public void createForm(String name, String ownerID, TextChannel channel, MessageEmbed embed, List<FormOption> emotes, FormReactionEvent reactionEvent) {
        Message message = channel.sendMessage(embed).complete();
        for(FormOption emote : emotes) {
            try {
                message.addReaction(EmojiParser.parseToUnicode(emote.getEmote())).complete();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        addForm(new Form(name, message, ownerID, emotes, reactionEvent, null));
    }

    public void createForm(String name, String ownerID, TextChannel channel, MessageEmbed embed, FormMessageEvent messageEvent) {
        Message message = channel.sendMessage(embed).complete();
        addForm(new Form(name, message, ownerID, null, null, messageEvent));
    }

}
