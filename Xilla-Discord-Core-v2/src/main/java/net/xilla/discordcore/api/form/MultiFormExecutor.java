package net.xilla.discordcore.api.form;

import net.xilla.discordcore.api.form.form.FormResponse;

import java.util.HashMap;

public interface MultiFormExecutor {

    void finish(HashMap<String, FormResponse> responses);

}
