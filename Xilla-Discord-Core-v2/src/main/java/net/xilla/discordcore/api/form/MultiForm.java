package net.xilla.discordcore.api.form;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.api.form.Form;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiForm extends CoreObject {

    private String name;
    private List<MessageFormBuilder> formBuilders;
    private HashMap<String, FormResponse> formResults;
    private int index;
    private MultiFormExecutor executor;

    public MultiForm(String name, MultiFormExecutor executor) {
        this.name = name;
        this.formResults = new HashMap<>();
        this.formBuilders = new ArrayList<>();
        this.index = 0;
        this.executor = executor;
    }

    public void start() {
        askQuestion();
    }

    public void finish() {
        executor.finish(formResults);
        index = formBuilders.size();
    }

    public void addMessageQuestion(String name, String question, TextChannel channel, String ownerID) {
        MessageFormBuilder builder = new MessageFormBuilder();
        builder.setName(name);
        builder.setTextChannel(channel);
        builder.setOwnerID(ownerID);
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle(name).setDescription(question).setColor(Color.decode(getCoreSetting().getEmbedColor()));
        builder.setMessage(embedBuilder.build());
        builder.setFormMessageEvent((form, event) -> {
            formResults.put(name, new FormResponse(name, question, event.getMessage().getContentRaw()));
            form.getMessage().delete().queue();
            event.getMessage().delete().queue();
            askQuestion();
            return true;
        });
        formBuilders.add(builder);
    }

    private void askQuestion() {
        if(index < formBuilders.size()) {
            MessageFormBuilder builder = formBuilders.get(index);
            Form form = builder.build();
            builder.register(form);
            index++;
        } else {
            finish();
        }
    }

}
