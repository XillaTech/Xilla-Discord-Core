package net.xilla.discordcore.core;

import net.xilla.core.library.Pair;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Arrays;
import java.util.List;

public class CoreScript {

    private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("groovy");
    private static final List<String> DEFAULT_IMPORTS = Arrays.asList("net.dv8tion.jda.api.entities.impl", "net.dv8tion.jda.api.managers", "net.dv8tion.jda.api.entities", "net.dv8tion.jda.api",
            "java.io", "java.math", "java.util", "java.util.concurrent", "java.time", "java.util.stream", "com.vdurmont.emoji", "net.xilla.discordcore", "net.xilla.discordcore.core"
    );

    // Very dangerous, please use responsibly
    public static Pair<String, Object> run(String code, List<Pair<String, Object>> data) {

        String status = "Success";
        Object out;

        for(Pair<String, Object> temp : data) {
            SCRIPT_ENGINE.put(temp.getValueOne(), temp.getValueTwo());
        }

        StringBuilder imports = new StringBuilder();
        DEFAULT_IMPORTS.forEach(imp -> imports.append("import ").append(imp).append(".*; "));
        long start = System.currentTimeMillis();
        try {
            out = SCRIPT_ENGINE.eval(imports + code);
        } catch (Exception e) {
            out = e.getMessage();
            status = "Failed";
        }

        return new Pair<>(status, out);
    }

}
