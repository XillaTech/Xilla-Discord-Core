package net.xilla.discordcore.command.template.type;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.core.library.Pair;
import net.xilla.core.library.json.XillaJson;
import net.xilla.discordcore.command.template.TemplateCommand;
import net.xilla.discordcore.core.CoreScript;
import net.xilla.discordcore.core.command.response.CommandResponse;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScriptCommand extends TemplateCommand {

    private String code;

    public ScriptCommand(String module, String name, String[] activators, String description, String usage, String text, String permission) {
        super(module, name, activators, description, usage, permission, (data) -> new CommandResponse(data).setDescription(text));
        this.code = text;
    }

    public ScriptCommand(JSONObject map) {
        super(
                map.get("module").toString(),
                map.get("name").toString(),
                map.get("activators").toString().split(","),
                map.get("description").toString(), map.get("usage").toString(),
                map.get("permission"),
                (data) -> {
                    MessageReceivedEvent event = null;
                    if(data.get() instanceof MessageReceivedEvent) {
                        event = (MessageReceivedEvent) data.get();
                    }

                    List<Pair<String, Object>> scriptData = new ArrayList<>();

                    scriptData.add(new Pair<>("event", event));
                    scriptData.add(new Pair<>("message", event.getMessage()));
                    scriptData.add(new Pair<>("channel", event.getChannel()));
                    scriptData.add(new Pair<>("args", data.getArgs()));
                    scriptData.add(new Pair<>("api", event.getJDA()));
                    scriptData.add(new Pair<>("jda", event.getJDA()));
                    scriptData.add(new Pair<>("guild", event.getGuild()));
                    scriptData.add(new Pair<>("member", event.getMember()));

                    long start = System.currentTimeMillis();
                    Pair<String, Object> response = CoreScript.run(map.get("code").toString(), scriptData);
                    Object out = response.getValueTwo();
                    if(response.getValueOne().equals("Success")) {
                        if(out instanceof EmbedBuilder) {
                            return new CoreCommandResponse(data).setEmbed(((EmbedBuilder)out).build());
                        } else if(out instanceof MessageEmbed) {
                            return new CoreCommandResponse(data).setEmbed(((MessageEmbed)out));
                        } else {
                            return new CoreCommandResponse(data).setDescription(out.toString());
                        }
                    } else {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setTitle("Eval")
                                .setColor(Color.RED)
                                .addField("Status:", "Failed", true)
                                .addField("Duration:", (System.currentTimeMillis() - start) + "ms", true)
                                .addField("Result:", out == null ? "" : out.toString(), false);
                        return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
                    }
                }
        );
        this.code = map.get("code").toString();
    }

    @Override
    public XillaJson getSerializedData() {
        JSONObject map = new JSONObject();
        map.put("name", getKey());
        map.put("module", getModule());
        map.put("description", getDescription());
        map.put("usage", getUsage());
        map.put("permission", getPermission());
        map.put("type", "Script");

        StringBuilder activatorsParsed = new StringBuilder();
        for(int i = 0; i < getActivators().length; i++) {
            activatorsParsed.append(getActivators()[i]);
            if(i != getActivators().length - 1) {
                activatorsParsed.append(",");
            }
        }

        map.put("activators", activatorsParsed.toString());

        map.put("code", code);

        return new XillaJson(new JSONObject(map));
    }

    @Override
    public void setData(String text) {
        this.code = text;

        setExecutors(Collections.singletonList((data) -> {
            MessageReceivedEvent event = null;
            if(data.get() instanceof MessageReceivedEvent) {
                event = (MessageReceivedEvent) data.get();
            }

            List<Pair<String, Object>> scriptData = new ArrayList<>();

            scriptData.add(new Pair<>("event", event));
            scriptData.add(new Pair<>("message", event.getMessage()));
            scriptData.add(new Pair<>("channel", event.getChannel()));
            scriptData.add(new Pair<>("args", data.getArgs()));
            scriptData.add(new Pair<>("api", event.getJDA()));
            scriptData.add(new Pair<>("jda", event.getJDA()));
            scriptData.add(new Pair<>("guild", event.getGuild()));
            scriptData.add(new Pair<>("member", event.getMember()));

            long start = System.currentTimeMillis();
            Pair<String, Object> response = CoreScript.run(code, scriptData);
            Object out = response.getValueTwo();
            if(response.getValueOne().equals("Success")) {
                if(out instanceof EmbedBuilder) {
                    return new CoreCommandResponse(data).setEmbed(((EmbedBuilder)out).build());
                } else if(out instanceof MessageEmbed) {
                    return new CoreCommandResponse(data).setEmbed(((MessageEmbed)out));
                } else {
                    if(out == null) {
                        return null;
                    }
                    return new CoreCommandResponse(data).setDescription(out.toString());
                }
            } else {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Eval")
                        .setColor(Color.RED)
                        .addField("Status:", "Failed", true)
                        .addField("Duration:", (System.currentTimeMillis() - start) + "ms", true)
                        .addField("Result:", out == null ? "" : out.toString(), false);
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            }
        }));
    }

    @Override
    public String getData() {
        return code;
    }

}
