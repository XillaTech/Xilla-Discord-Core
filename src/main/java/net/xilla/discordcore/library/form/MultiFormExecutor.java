package net.xilla.discordcore.library.form;

import net.xilla.discordcore.library.form.form.FormResponse;

import java.util.HashMap;

public interface MultiFormExecutor {

    void finish(HashMap<String, FormResponse> responses);

}
