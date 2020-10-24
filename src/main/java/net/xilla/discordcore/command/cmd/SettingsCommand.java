package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.settings.GuildSettings;
import net.xilla.discordcore.settings.Settings;

import java.util.ArrayList;

public class SettingsCommand extends CoreObject {

    public SettingsCommand() {
        CommandBuilder builder = new CommandBuilder("Admin", "Settings");
        builder.setActivators("settings", "s");
        builder.setPermission("settings.admin");
        builder.setDescription("Adjust the bots settings!");
        builder.setCommandExecutor(data -> {
            StringBuilder description = null;

            MessageReceivedEvent event = (MessageReceivedEvent)data.get();

            if(data.getArgs().length == 1 && data.getArgs()[0].equalsIgnoreCase("list")) {
                description = new StringBuilder();

                description.append("**Configs:** ");

                ArrayList<GuildSettings> values = new ArrayList<>(getDiscordCore().getGuildSettingsManager().getData().values());
                for(int i = 0; i < values.size(); i++) {
                    description.append(values.get(i).getKey());

                    if(i != values.size() - 1) {
                        description.append(", ");
                    }
                }
            } else if(data.getArgs().length == 2 && data.getArgs()[0].equalsIgnoreCase("info")) {
                description = new StringBuilder();

                GuildSettings settings = getDiscordCore().getGuildSettingsManager().get(data.getArgs()[1]);
                if(settings == null) {
                    description.append("That is not a valid config!");
                } else {
                    Settings guildSettings = settings.getSettings(event.getGuild().getId());
                    description.append("**Settings:** ```");

                    for(Object key : guildSettings.getConfig().getJson().getJson().keySet()) {
                        Object value = guildSettings.getConfig().get(key.toString());

                        if(value instanceof String) {
                            description.append(key.toString()).append(": ").append(value.toString()).append("\n");
                        } else if(value instanceof Number) {
                            description.append(key.toString()).append(": ").append(value.toString()).append("\n");
                        } else if(value instanceof Boolean) {
                            description.append(key.toString()).append(": ").append(value.toString()).append("\n");
                        }
                    }

                    description.append("```");
                }
            } else if(data.getArgs().length >= 4 && data.getArgs()[0].equalsIgnoreCase("set")) {
                description = new StringBuilder();

                GuildSettings settings = getDiscordCore().getGuildSettingsManager().get(data.getArgs()[1]);
                if(settings == null) {
                    description.append("That is not a valid config!");
                } else {
                    Settings guildSettings = settings.getSettings(event.getGuild().getId());

                    String key = data.getArgs()[2];

                    StringBuilder value = new StringBuilder();
                    for(int i = 3; i < data.getArgs().length; i++) {
                        value.append(data.getArgs()[i]);
                        if(i != data.getArgs().length - 1) {
                            value.append(" ");
                        }
                    }

                    Object obj = guildSettings.getConfig().get(key);
                    if(obj == null) {
                        description.append("That is not a valid settings option!");
                    } else {
                        if(obj instanceof Number) {
                            try {
                                if (obj instanceof Double) {
                                    guildSettings.getConfig().set(key, Double.parseDouble(value.toString()));
                                    guildSettings.getConfig().save();
                                    description.append("Successfully updated that config value!");
                                } else if (obj instanceof Integer) {
                                    guildSettings.getConfig().set(key, Integer.parseInt(value.toString()));
                                    guildSettings.getConfig().save();
                                    description.append("Successfully updated that config value!");
                                } else if (obj instanceof Long) {
                                    guildSettings.getConfig().set(key, Long.parseLong(value.toString()));
                                    guildSettings.getConfig().save();
                                    description.append("Successfully updated that config value!");
                                } else if (obj instanceof Float) {
                                    guildSettings.getConfig().set(key, Float.parseFloat(value.toString()));
                                    guildSettings.getConfig().save();
                                    description.append("Successfully updated that config value!");
                                } else if (obj instanceof Byte) {
                                    guildSettings.getConfig().set(key, Byte.parseByte(value.toString()));
                                    guildSettings.getConfig().save();
                                    description.append("Successfully updated that config value!");
                                } else if (obj instanceof Short) {
                                    guildSettings.getConfig().set(key, Short.parseShort(value.toString()));
                                    guildSettings.getConfig().save();
                                    description.append("Successfully updated that config value!");
                                } else {
                                    description.append("Error, invalid number!");
                                }
                            } catch (Exception ex) {
                                description.append("Error parsing your input!");
                            }
                        } else if(obj instanceof Boolean) {
                            try {
                                guildSettings.getConfig().set(key, Boolean.parseBoolean(value.toString()));
                                guildSettings.getConfig().save();
                                description.append("Successfully updated that config value!");
                            } catch (Exception ex) {
                                description.append("Error parsing your input!");
                            }
                        } else if(obj instanceof String) {
                            guildSettings.getConfig().set(key, value.toString());
                            guildSettings.getConfig().save();
                            description.append("Successfully updated that config value!");
                        } else {
                            description.append("You can only edit Strings, Numbers, and Booleans");
                        }
                    }
                }
            } else if(data.getArgs().length >= 3 && data.getArgs()[0].equalsIgnoreCase("block")) {

                StringBuilder value = new StringBuilder();
                for(int i = 2; i < data.getArgs().length; i++) {
                    value.append(data.getArgs()[i]);
                    if(i != data.getArgs().length - 1) {
                        value.append(" ");
                    }
                }

                if(value.toString().equalsIgnoreCase("settings")
                                || value.toString().equalsIgnoreCase("s")
                                || value.toString().equalsIgnoreCase("admin")
                                || value.toString().equalsIgnoreCase("core")) {
                    description = new StringBuilder();
                    description.append("You cannot block that command/module.");
                }

                if(description == null && data.getArgs()[1].equalsIgnoreCase("module")) {
                    description = new StringBuilder();

                    DiscordCore.getInstance().getServerSettings().addModule(event.getGuild(), value.toString().toLowerCase());

                    description.append("You have blocked that module.");
                }
                if(description == null && data.getArgs()[1].equalsIgnoreCase("command")) {
                    description = new StringBuilder();

                    DiscordCore.getInstance().getServerSettings().addCommand(event.getGuild(), value.toString().toLowerCase());

                    description.append("You have blocked that command.");
                }
            } else if(data.getArgs().length >= 3 && data.getArgs()[0].equalsIgnoreCase("unblock")) {

                StringBuilder value = new StringBuilder();
                for(int i = 2; i < data.getArgs().length; i++) {
                    value.append(data.getArgs()[i]);
                    if(i != data.getArgs().length - 1) {
                        value.append(" ");
                    }
                }

                if(data.getArgs()[1].equalsIgnoreCase("module")) {
                    description = new StringBuilder();

                    DiscordCore.getInstance().getServerSettings().removeModule(event.getGuild(), value.toString().toLowerCase());

                    description.append("You have unblocked that module.");
                }
                if(data.getArgs()[1].equalsIgnoreCase("command")) {
                    description = new StringBuilder();

                    DiscordCore.getInstance().getServerSettings().removeCommand(event.getGuild(), value.toString().toLowerCase());

                    description.append("You have unblocked that command.");
                }
            }

            if(description == null) {
                description = new StringBuilder();

                description.append(getPrefix()).append("s set (config) (key) (value) - Set value\n");
                description.append(getPrefix()).append("s info (config) - List config information\n");
                description.append(getPrefix()).append("s block module (module) - Block a module\n");
                description.append(getPrefix()).append("s block command (command) - Block a command\n");
                description.append(getPrefix()).append("s unblock module (module) - Unblock a module\n");
                description.append(getPrefix()).append("s unblock command (command) - Unblock a command\n");
                description.append(getPrefix()).append("s list - List available configs\n");
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Server Settings");
            if(event != null) {
                embedBuilder.setColor(getColor(event.getGuild()));
            }
            embedBuilder.setDescription(description.toString());
            return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
        });
        builder.build();
    }

}
