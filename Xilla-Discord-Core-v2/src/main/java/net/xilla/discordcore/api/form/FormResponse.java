package net.xilla.discordcore.api.form;

import net.xilla.discordcore.CoreObject;

public class FormResponse extends CoreObject {

    private String name;
    private String question;
    private String response;

    public FormResponse(String name, String question, String response) {
        this.name = name;
        this.question = question;
        this.response = response;
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
}
