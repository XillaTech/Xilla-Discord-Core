package net.xilla.discordcore.command.cmd;

import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.settings.Settings;
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
                    .append("settings set <config> <setting> <value> - Set a setting's value\n").append(getCoreSetting().getCommandPrefix())
                    .append("settings info <config> - View a config's settings\n").append(getCoreSetting().getCommandPrefix())
                    .append("settings list - List available settings\n");
            EmbedBuilder builder = new EmbedBuilder().setTitle("Settings");
            builder.setColor(Color.decode(getCoreSetting().getEmbedColor()));
            builder.setDescription(description);

            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                StringBuilder configs = new StringBuilder();
                configs.append("**Available Configs**\n");
                for(String settingsName : DiscordCore.getInstance().getSettingsManager().getSettingsNames()) {
                    configs.append("> ").append(settingsName).append("\n");
                }
                configs.append("\n").append(DiscordCore.getInstance().getSettings().getCommandPrefix()).append("settings info <config>");
                configs.append("\n").append(DiscordCore.getInstance().getSettings().getCommandPrefix()).append("settings set <config> <setting> <value>");
                builder.setDescription(configs.toString());
            } else if (args.length == 2 && args[0].equalsIgnoreCase("info")) {
                Settings settings = DiscordCore.getInstance().getSettingsManager().getSettings(args[1].toLowerCase());
                if(settings != null) {
                    StringBuilder configs = new StringBuilder();
                    configs.append("**Available Settings** (").append(settings.getKey()).append(")\n\n");
                    for (Object object : settings.getConfig().getJSON().keySet()) {
                        if(settings.getConfig().getJSON().get(object) instanceof String) {
                            if (!object.toString().equalsIgnoreCase("token")) {
                                configs.append("> ").append(object.toString()).append(": `").append(settings.getConfig().get(object.toString()).toString()).append("`\n");
                            }
                        }
                    }
                    configs.append("\n").append(DiscordCore.getInstance().getSettings().getCommandPrefix()).append("settings set <config> <setting> <value>");
                    builder.setDescription(configs.toString());
                } else {
                    builder.setDescription("That is not a valid config!");
                }
            } else if (args.length >= 4 && args[0].equalsIgnoreCase("set")) {
                Settings settings = DiscordCore.getInstance().getSettingsManager().getSettings(args[1].toLowerCase());
                if (settings != null) {
                    if(settings.getConfig().getJSON().containsKey(args[2])) {
                        if(settings.getConfig().getJSON().get(args[2]) instanceof String) {
                            String value = "";
                            for (int i = 3; i < args.length; i++) {
                                value = value + args[i];
                                if (i != args.length - 1) {
                                    value = value + " ";
                                }
                            }
                            settings.getConfig().set(args[2], value);
                            settings.getConfig().save();
                            builder.setDescription("You have successfully updated that message.");
                        } else {
                            builder.setDescription("That is not a valid setting!");
                        }
                    } else {
                        builder.setDescription("That is not a valid setting!");
                    }
                } else {
                    builder.setDescription("That is not a valid config!");
                }
            }



            return new CoreCommandResponse(data).setEmbed(builder.build());
        };
    }
}
