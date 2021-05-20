package net.xilla.discord.manager.command.discord.executors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.xilla.core.library.Pair;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.api.command.CommandExecutor;
import net.xilla.discord.api.command.CommandInput;
import net.xilla.discord.api.embed.EmbedFormat;

import java.util.HashMap;
import java.util.Locale;

public class SectionSelector extends HashMap<String, Pair<String, CommandExecutor>> implements CommandExecutor {

    private int argument;

    private String template = null;

    public SectionSelector(int argument) {
        this.argument = argument;
    }

    public SectionSelector(String template, int argument) {
        this.argument = argument;
        this.template = template;
    }

    @Override
    public Pair<String, CommandExecutor> put(String key, Pair<String, CommandExecutor> value) {
        return super.put(key.toLowerCase(Locale.ROOT), value);
    }

    public Pair<String, CommandExecutor> put(String key, String description, CommandExecutor executor) {
        return put(key, new Pair<>(description, executor));
    }

    @Override
    public void run(CommandInput input) throws Exception {

        if(input.getArgs().length < argument + 1) {
            send(input);
            return;
        }

        String arg = input.getArgs()[argument].toLowerCase(Locale.ROOT);

        if(containsKey(arg)) {
            Pair<String, CommandExecutor> data = get(arg);

            CommandExecutor executor = data.getValueTwo();

            executor.run(input);
        } else {
            send(input);
        }
    }

    private void send(CommandInput input) {
        StringBuilder builder = new StringBuilder();

        forEach((key, value) -> {
            builder.append(key).append(": ").append(value.getValueOne()).append("\n");
        });

        if(template == null) {
            input.getEvent().getChannel().sendMessage(builder.toString()).queue();
        } else {
            EmbedFormat format = DiscordCore.getInstance().getEmbedProcessor().get(template);
            MessageEmbed embed = format.buildTemplate(input.getEvent().getMember());
            EmbedBuilder embedBuilder = new EmbedBuilder(embed);

            embedBuilder.setDescription(builder);

            input.getEvent().getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }

}
