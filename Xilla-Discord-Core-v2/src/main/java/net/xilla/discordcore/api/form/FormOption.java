package net.xilla.discordcore.api.form;

import com.vdurmont.emoji.EmojiParser;

public class FormOption {

    private String emote;
    private String option;

    private FormOption(String emote, String option) {
        this.emote = EmojiParser.parseToUnicode(emote);
        this.option = option;
    }

    public String getEmote() {
        return emote;
    }

    public String getOption() {
        return option;
    }
}
