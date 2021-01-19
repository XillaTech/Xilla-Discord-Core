package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.core.library.Pair;
import net.xilla.discordcore.library.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.CoreScript;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class EvalCommand implements CoreObject {

    public EvalCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Core", "eval", false);
        commandBuilder.setDescription("Executes (potentially unsafe) Java and Javascript code. **Only use if you know what you're doing**");
        commandBuilder.setPermission("core.eval");
        commandBuilder.setCommandExecutor((data) -> {
            MessageReceivedEvent event = (MessageReceivedEvent) data.get();

            List<Pair<String, Object>> scriptData = new ArrayList<>();

            scriptData.add(new Pair<>("event", event));
            scriptData.add(new Pair<>("message", event.getMessage()));
            scriptData.add(new Pair<>("channel", event.getChannel()));
            scriptData.add(new Pair<>("args", data.getArgs()));
            scriptData.add(new Pair<>("api", event.getJDA()));
            scriptData.add(new Pair<>("jda", event.getJDA()));
            scriptData.add(new Pair<>("guild", event.getGuild()));
            scriptData.add(new Pair<>("member", event.getMember()));

            String code = String.join(" ", data.getArgs());

            long start = System.currentTimeMillis();
            Pair<String, Object> response = CoreScript.run(code, scriptData);

            String status = response.getValueOne();
            Color color = Color.GREEN;
            if(status.equals("Failed")) {
                color = Color.RED;
            }

            Object out = response.getValueTwo();

            EmbedBuilder embedBuilder = new EmbedBuilder()
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
