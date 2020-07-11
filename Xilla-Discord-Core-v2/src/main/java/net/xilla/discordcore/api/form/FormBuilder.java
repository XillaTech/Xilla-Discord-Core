package net.xilla.discordcore.api.form;

public interface FormBuilder {

    void register(Form form);

    void register();

    Form build();

    String getType();
}
