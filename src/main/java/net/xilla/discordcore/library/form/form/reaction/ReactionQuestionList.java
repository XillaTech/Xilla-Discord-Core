package net.xilla.discordcore.library.form.form.reaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReactionQuestionList {

    private HashMap<String, ReactionQuestion> reactionMap;
    private List<ReactionQuestion> questions;

    public ReactionQuestionList() {
        this.reactionMap = new HashMap<>();
        this.questions = new ArrayList<>();
    }

    public void addQuestion(ReactionQuestion question) {
        reactionMap.put(question.getReaction(), question);
        questions.add(question);
    }

    public ReactionQuestion getQuestionByEmote(String emote) {
        return reactionMap.get(emote);
    }

    public List<ReactionQuestion> getQuestions() {
        return questions;
    }
}
