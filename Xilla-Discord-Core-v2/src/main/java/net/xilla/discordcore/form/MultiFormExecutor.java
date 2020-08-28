package net.xilla.discordcore.form;

import net.xilla.discordcore.form.form.FormResponse;

import java.util.HashMap;

public interface MultiFormExecutor {

    void finish(HashMap<String, FormResponse> responses);

}
