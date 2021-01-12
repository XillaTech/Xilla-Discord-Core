package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.permission.PermissionAPI;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class EvalCommand extends CoreObject {

    private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("groovy");
    private static final List<String> DEFAULT_IMPORTS = Arrays.asList("net.dv8tion.jda.api.entities.impl", "net.dv8tion.jda.api.managers", "net.dv8tion.jda.api.entities", "net.dv8tion.jda.api",
            "java.io", "java.math", "java.util", "java.util.concurrent", "java.time", "java.util.stream", "com.vdurmont.emoji", "net.xilla.discordcore", "net.xilla.discordcore.core"
    );

    public EvalCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Core", "eval", false);
        commandBuilder.setDescription("Executes (potentially unsafe) Java and Javascript code. **Only use if you know what you're doing**");
        commandBuilder.setCommandExecutor((data) -> {
            MessageReceivedEvent event = (MessageReceivedEvent) data.get();

            if (!PermissionAPI.hasPermission(event.getMember(), "core.eval")) return new CoreCommandResponse(data).setDescription("Improper Permissions. Missing `core.eval`");

            Color color = Color.GREEN;
            String status = "Success";
            Object out;
            SCRIPT_ENGINE.put("event", event);
            SCRIPT_ENGINE.put("message", event.getMessage());
            SCRIPT_ENGINE.put("channel", event.getChannel());
            SCRIPT_ENGINE.put("args", data.getArgs());
            SCRIPT_ENGINE.put("api", event.getJDA());
            SCRIPT_ENGINE.put("jda", event.getJDA());
            SCRIPT_ENGINE.put("guild", event.getGuild());
            SCRIPT_ENGINE.put("member", event.getMember());

            StringBuilder imports = new StringBuilder();
            DEFAULT_IMPORTS.forEach(imp -> imports.append("import ").append(imp).append(".*; "));
            String code = String.join(" ", data.getArgs());
            long start = System.currentTimeMillis();
            try {
                out = SCRIPT_ENGINE.eval(imports + code);
            } catch (Exception e) {
                out = e.getMessage();
                color = Color.RED;
                status = "Failed";
            }

            EmbedBuilder embedBuilder = getEmbed(event)
                    .setTitle("Eval")
                    .setColor(color)
                    .addField("Status:", status, true)
                    .addField("Duration:", (System.currentTimeMillis() - start) + "ms", true)
                    .addField("Code:", "```java\n" + code + "\n```", false)
                    .addField("Result:", out == null ? "" : out.toString(), false);
            return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
        });
        commandBuilder.build();
    }

}
