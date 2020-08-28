package net.xilla.discordcore.form.form;

import net.xilla.discordcore.CoreObject;

public class FormResponse extends CoreObject {

    private String name;
    private String question;
    private String response;
    private String emoji;

    public FormResponse(String name, String question, String response) {
        this.name = name;
        this.question = question;
        this.response = response;
    }

    public FormResponse(String name, String question, String response, String emoji) {
        this.name = name;
        this.question = question;
        this.response = response;
        this.emoji = emoji;
    }

    public String getName() {
        return name;
    }

    public String getQuestion() {
        return question;
    }

    public String getResponse() {
        return response;
    }

    public String getEmoji() {
        return emoji;
    }
}
