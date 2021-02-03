package net.xilla.discordcore.library.form;

import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.library.CoreObject;
import net.xilla.discordcore.library.form.form.Form;
import net.xilla.discordcore.library.form.form.FormBuilder;
import net.xilla.discordcore.library.form.form.FormOption;
import net.xilla.discordcore.library.form.form.FormResponse;
import net.xilla.discordcore.library.form.form.reaction.ReactionQuestion;
import net.xilla.discordcore.library.form.form.reaction.ReactionQuestionList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiForm implements CoreObject {

    private String name;
    private List<FormBuilder> formBuilders;
    private HashMap<String, FormResponse> formResults;
    private int index;
    private MultiFormExecutor executor;
    private String channelID;

    public MultiForm(String name, String channelID, MultiFormExecutor executor) {
        this.name = name;
        this.formResults = new HashMap<>();
        this.formBuilders = new ArrayList<>();
        this.index = 0;
        this.executor = executor;
        this.channelID = channelID;
    }

    public void start() {
        askQuestion();
    }

    public void finish() {
        try {
            executor.finish(formResults);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        index = formBuilders.size();
    }

    public void addMessageQuestion(String name, String question, String ownerID, String guildID) {
        MessageFormBuilder builder = new MessageFormBuilder();
        builder.setName(name);
        builder.setTextChannel(channelID);
        builder.setOwnerID(ownerID);
        EmbedBuilder embedBuilder;

        embedBuilder = getEmbed(ownerID, guildID);

        embedBuilder.setTitle(name).setDescription(question);
        builder.setMessage(embedBuilder.build());
        builder.setFormMessageEvent((form, event) -> {
            formResults.put(name, new FormResponse(name, question, event.getMessage().getContentRaw()));
            if(form.getMessage() != null) {
                form.getMessage().delete().queue();
            }
            event.getMessage().delete().queue();
            askQuestion();
            return true;
        });
        formBuilders.add(builder);
    }

    public void addReactionQuestion(String name, String question, ReactionQuestionList questions, String ownerID, String guildID) {
        ReactionFormBuilder builder = new ReactionFormBuilder();
        builder.setName(name);
        builder.setTextChannel(channelID);
        builder.setOwnerID(ownerID);
        EmbedBuilder embedBuilder;

        embedBuilder = getEmbed(ownerID, guildID);

        embedBuilder.setTitle(name).setDescription(question);
        builder.setMessage(embedBuilder.build());

        List<FormOption> formOptions = new ArrayList<>();

        for(ReactionQuestion q : questions.getQuestions()) {
            formOptions.add(new FormOption(q.getReaction(), q.getResponse()));
        }

        builder.setOptions(formOptions);
        builder.setFormReactionEvent((form, event) -> {
            formResults.put(name, new FormResponse(name, question, questions.getQuestionByEmote(event.getReactionEmote().getEmoji()).getResponse(), event.getReactionEmote().getEmoji()));
            form.getMessage().delete().queue();
            askQuestion();
            return true;
        });
        formBuilders.add(builder);
    }

    private void askQuestion() {
        if(index < formBuilders.size()) {
            FormBuilder builder = formBuilders.get(index);
            Form form = builder.build(channelID);
            builder.register(form);
            index++;
        } else {
            finish();
        }
    }

}
