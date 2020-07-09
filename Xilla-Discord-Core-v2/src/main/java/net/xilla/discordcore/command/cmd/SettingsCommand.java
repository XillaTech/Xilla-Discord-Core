package net.xilla.discordcore.command.cmd;

import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.response.CoreCommandResponse;

import java.awt.*;

public class SettingsCommand extends CoreObject {

    public SettingsCommand() {
        CommandBuilder commandBuilder = new CommandBuilder();
        commandBuilder.setModule("Core");
        commandBuilder.setName("Settings");
        commandBuilder.setActivators("settings");
        commandBuilder.setDescription("View and manage your bot settings");
        commandBuilder.setPermission("core.settings");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CommandExecutor getExecutor() {
        return (command, args, data) -> {

            StringBuilder description = new StringBuilder();
            description.append("*Available Commands*\n").append(getCoreSetting().getCommandPrefix())
                    .append("settings set <setting> <value> - Set a setting's value\n").append(getCoreSetting().getCommandPrefix())
                    .append("settings info <setting> - View a setting's value\n").append(getCoreSetting().getCommandPrefix())
                    .append("settings list - List available settings\n");


            EmbedBuilder builder = new EmbedBuilder().setTitle("Settings");
            builder.setDescription(description);
            builder.setColor(Color.decode(getCoreSetting().getEmbedColor()));

            return new CoreCommandResponse(data).setEmbed(builder.build());
        };
    }
}
