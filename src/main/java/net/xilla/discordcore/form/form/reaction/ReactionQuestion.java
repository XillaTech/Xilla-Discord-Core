package net.xilla.discordcore.form.form.reaction;

import com.vdurmont.emoji.EmojiParser;

public class ReactionQuestion {

    private String reaction;
    private String response;

    public ReactionQuestion(String reaction, String response) {
        this.reaction = EmojiParser.parseToUnicode(reaction);
        this.response = response;
    }

    public String getReaction() {
        return reaction;
    }

    public String getResponse() {
        return response;
    }
}
