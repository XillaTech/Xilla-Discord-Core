package net.xilla.discordcore.api.form;

import java.util.HashMap;

public interface MultiFormExecutor {

    void finish(HashMap<String, FormResponse> responses);

}
