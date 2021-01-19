package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.library.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.command.CommandExecutor;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.settings.DiscordSettings;

public class CoreSettingsCommand implements CoreObject {

    public CoreSettingsCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Core", "CoreSettings", true);
        commandBuilder.setActivators("coresettings", "cs");
        commandBuilder.setDescription("View and manage your bot settings");
        commandBuilder.setPermission("core.settings");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CommandExecutor getExecutor() {
        return (data) -> {

            StringBuilder description = new StringBuilder();
            description.append("*Available Commands*\n").append(getCoreSetting().getCommandPrefix())
                    .append("cs set <config> <setting> <value> - Set a setting's value\n").append(getCoreSetting().getCommandPrefix())
                    .append("cs info <config> - View a config's settings\n").append(getCoreSetting().getCommandPrefix())
                    .append("cs list - List available settings\n");
            EmbedBuilder builder = new EmbedBuilder();

            if(data.get() instanceof MessageReceivedEvent) {
                builder = getEmbed((MessageReceivedEvent)data.get());
            }

            builder.setTitle("Settings");
            builder.setDescription(description);

            if (data.getArgs().length == 1 && data.getArgs()[0].equalsIgnoreCase("list")) {
                StringBuilder configs = new StringBuilder();
                configs.append("**Available Configs**\n");
                for(String settingsName : DiscordCore.getInstance().getSettingsManager().getSettingsNames()) {
                    configs.append("> ").append(settingsName).append("\n");
                }
                configs.append("\n").append(DiscordCore.getInstance().getSettings().getCommandPrefix()).append("settings info <config>");
                configs.append("\n").append(DiscordCore.getInstance().getSettings().getCommandPrefix()).append("settings set <config> <setting> <value>");
                builder.setDescription(configs.toString());
            } else if (data.getArgs().length == 2 && data.getArgs()[0].equalsIgnoreCase("info")) {
                DiscordSettings settings = DiscordCore.getInstance().getSettingsManager().get(data.getArgs()[1].toLowerCase());
                if(settings != null) {
                    StringBuilder configs = new StringBuilder();
                    configs.append("**Available Settings** (").append(settings.getKey()).append(")\n\n");
                    for (Object object : settings.getConfig().getJson().getJson().keySet()) {
                        if(settings.getConfig().getJson().getJson().get(object) instanceof String) {
                            if (!object.toString().equalsIgnoreCase("token")) {
                                configs.append("> ").append(object.toString()).append(": `").append(settings.getConfig().get(object.toString()).toString()).append("`\n");
                            }
                        }
                    }
                    configs.append("\n").append(DiscordCore.getInstance().getSettings().getCommandPrefix()).append("cs set <config> <setting> <value>");
                    builder.setDescription(configs.toString());
                } else {
                    builder.setDescription("That is not a valid config!");
                }
            } else if (data.getArgs().length >= 4 && data.getArgs()[0].equalsIgnoreCase("set")) {
                DiscordSettings settings = DiscordCore.getInstance().getSettingsManager().get(data.getArgs()[1].toLowerCase());
                if (settings != null) {
                    if(settings.getConfig().getJson().getJson().containsKey(data.getArgs()[2])) {
                        if(settings.getConfig().getJson().getJson().get(data.getArgs()[2]) instanceof String) {
                            String value = "";
                            for (int i = 3; i < data.getArgs().length; i++) {
                                value = value + data.getArgs()[i];
                                if (i != data.getArgs().length - 1) {
                                    value = value + " ";
                                }
                            }
                            settings.getConfig().set(data.getArgs()[2], value);
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
