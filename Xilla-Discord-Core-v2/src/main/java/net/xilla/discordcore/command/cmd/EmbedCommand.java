package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.response.CoreCommandResponse;

import java.awt.*;

public class EmbedCommand extends CoreObject {

    public EmbedCommand() {
        embedCommand();
    }

    public void embedCommand() {
        CommandBuilder builder = new CommandBuilder("Core", "Embed");
        builder.setActivators("embed", "em");
        builder.setDescription("Put your message in an embed.");
        builder.setPermission("core.embed");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                event.getMessage().delete().queue();

                StringBuilder message = new StringBuilder();
                for (int i = 0; i < data.getArgs().length; i++) {
                    String word = data.getArgs()[i];
                    message.append(word);
                    if (i != data.getArgs().length - 1)
                        message.append(" ");

                }

                String title = null;
                String description = null;
                if (message.toString().startsWith("\"")) {
                    String temp = message.toString().substring(1);
                    int index = temp.indexOf("\"");
                    title = temp.substring(0, index);
                    description = message.toString().substring(index + 2);
                }

                EmbedBuilder embedBuilder = new EmbedBuilder();

                if (title != null) {
                    embedBuilder.setTitle(title);
                    embedBuilder.setDescription(description);
                } else {
                    embedBuilder.setDescription(message.toString());
                }

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }
}
