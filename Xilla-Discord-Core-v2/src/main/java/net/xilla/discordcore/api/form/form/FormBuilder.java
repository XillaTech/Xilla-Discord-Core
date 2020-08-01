package net.xilla.discordcore.api.form.form;

public interface FormBuilder {

    void register(Form form);

    void register(String channelID);

    Form build(String channelID);

    String getType();
}
