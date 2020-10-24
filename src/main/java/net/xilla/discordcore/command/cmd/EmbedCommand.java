package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.CoreCommandExecutor;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.embed.EmbedStorage;
import net.xilla.discordcore.embed.JSONEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmbedCommand extends CoreObject {

    public EmbedCommand() {
        embedCommand();
        embedManagerCommand();
    }

    public void embedCommand() {
        CommandBuilder builder = new CommandBuilder("Admin", "Embed");
        builder.setActivators("embed", "em");
        builder.setDescription("Put your message in an embed.");
        builder.setPermission("embed.use");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                if(data.getArgs().length > 0) {

                    MessageReceivedEvent event = (MessageReceivedEvent) data.get();
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


                    if (title != null) {
                        embedBuilder.setTitle(title);
                        embedBuilder.setDescription(description);
                    } else {
                        embedBuilder.setDescription(message.toString());
                    }

                } else {
                    embedBuilder.setDescription(getPrefix() + "em (description)\n" + getPrefix() + "em \"(title)\" (description)");
                }
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void embedManagerCommand() {
        CommandBuilder builder = new CommandBuilder("Core", "EmbedManager");
        builder.setActivators("embedmanager", "eb");
        builder.setDescription("Edit the bot's embeds.");
        builder.setPermission("core.embedmanager");
        builder.setCommandExecutor((data) -> {
            StringBuilder response = new StringBuilder();

            if(data.getArgs().length == 1 && data.getArgs()[0].equalsIgnoreCase("list")) {
                response.append("**Available Categories**\n");
                for (EmbedStorage storage : new ArrayList<>(DiscordCore.getInstance().getEmbedManager().getData().values())) {
                    response.append("> ").append(storage.getKey()).append("\n");
                }
            } else if(data.getArgs().length == 2 && data.getArgs()[0].equalsIgnoreCase("info")) {
                EmbedStorage storage = DiscordCore.getInstance().getEmbedManager().get(data.getArgs()[1]);
                if(storage != null) {
                    response.append("Embeds (").append(storage.getEmbedMap().keySet().size()).append("): `");

                    Map<String, JSONEmbed> embeds = new HashMap<>(storage.getEmbedMap());
                    int loop = 0;
                    for(String str : embeds.keySet()) {
                        response.append(str);
                        if(loop != embeds.size() - 1) {
                            response.append(", ");
                        }
                        loop++;
                    }

                    response.append("`");
                } else {
                    response.append("That is not a valid category.");
                }
            } else if(data.getArgs().length == 3 && data.getArgs()[0].equalsIgnoreCase("info")) {
                EmbedStorage storage = DiscordCore.getInstance().getEmbedManager().get(data.getArgs()[1]);
                if(storage != null) {
                    JSONEmbed jsonEmbed = storage.getEmbed(data.getArgs()[2]);
                    if(jsonEmbed != null) {

                        MessageReceivedEvent event = (MessageReceivedEvent) data.get();

                        event.getTextChannel().sendMessage(jsonEmbed.build()).queue();
                        return null;
                    } else {
                        response.append("That is not a valid embed.");
                    }
                } else {
                    response.append("That is not a valid category.");
                }
            } else if(data.getArgs().length >= 3 && data.getArgs()[0].equalsIgnoreCase("edit")) {

                EmbedStorage storage = DiscordCore.getInstance().getEmbedManager().get(data.getArgs()[1]);
                if(storage != null) {
                    JSONEmbed jsonEmbed = storage.getEmbed(data.getArgs()[2]);
                    if(jsonEmbed != null) {

                        if(data.getArgs().length >= 4 && data.getArgs()[3].equalsIgnoreCase("title")) {
                            if(data.getArgs().length > 4) {
                                jsonEmbed.getEmbedBuilder().setTitle(smooshData(4, data.getArgs()));
                                response.append("You have set the title to `").append(smooshData(4, data.getArgs())).append("`.");
                            } else {
                                jsonEmbed.getEmbedBuilder().setTitle(null);
                                response.append("You have cleared the title.");
                            }
                            storage.save();
                        } else if(data.getArgs().length >= 4 && data.getArgs()[3].equalsIgnoreCase("description")) {
                            if(data.getArgs().length > 4) {
                                jsonEmbed.getEmbedBuilder().setDescription(smooshData(4, data.getArgs()));
                                response.append("You have set the description to `").append(smooshData(4, data.getArgs())).append("`.");
                            } else {
                                jsonEmbed.getEmbedBuilder().setTitle(null);
                                response.append("You have cleared the description.");
                            }
                            storage.save();
                        } else if(data.getArgs().length >= 4 && data.getArgs()[3].equalsIgnoreCase("footer")) {
                            if(data.getArgs().length > 4) {
                                jsonEmbed.getEmbedBuilder().setFooter(smooshData(4, data.getArgs()));
                                response.append("You have set the footer to `").append(smooshData(4, data.getArgs())).append("`.");
                            } else {
                                jsonEmbed.getEmbedBuilder().setTitle(null);
                                response.append("You have cleared the footer.");
                            }
                            storage.save();
                        } else if(data.getArgs().length >= 4 && data.getArgs()[3].equalsIgnoreCase("author")) {
                            if(data.getArgs().length > 4) {
                                if(jsonEmbed.getEmbedBuilder().build().getAuthor() != null && jsonEmbed.getEmbedBuilder().build().getAuthor().getUrl() != null) {
                                    jsonEmbed.getEmbedBuilder().setAuthor(smooshData(4, data.getArgs()), jsonEmbed.getEmbedBuilder().build().getAuthor().getUrl());
                                } else {
                                    jsonEmbed.getEmbedBuilder().setAuthor(smooshData(4, data.getArgs()));
                                }
                                response.append("You have set the author to `").append(smooshData(4, data.getArgs())).append("`.");
                            } else {
                                jsonEmbed.getEmbedBuilder().setTitle(null);
                                response.append("You have cleared the author.");
                            }
                            storage.save();
                        } else if(data.getArgs().length >= 4 && data.getArgs()[3].equalsIgnoreCase("url")) {
                            if(data.getArgs().length > 4) {
                                if(jsonEmbed.getEmbedBuilder().build().getAuthor() != null) {
                                    jsonEmbed.getEmbedBuilder().setAuthor(jsonEmbed.getEmbedBuilder().build().getAuthor().getName(), smooshData(4, data.getArgs()));
                                    response.append("You have set the url to `").append(smooshData(4, data.getArgs())).append("`.");
                                } else {
                                    response.append("You must set the author first before you can set the url.");
                                }
                            } else {
                                jsonEmbed.getEmbedBuilder().setTitle(null);
                                response.append("You have cleared the url.");
                            }
                            storage.save();
                        } else if(data.getArgs().length >= 4 && data.getArgs()[3].equalsIgnoreCase("image")) {
                            if(data.getArgs().length > 4) {
                                jsonEmbed.getEmbedBuilder().setFooter(smooshData(4, data.getArgs()));
                                response.append("You have set the image to `").append(smooshData(4, data.getArgs())).append("`.");
                            } else {
                                jsonEmbed.getEmbedBuilder().setTitle(null);
                                response.append("You have cleared the image.");
                            }
                            storage.save();
                        } else if(data.getArgs().length >= 4 && data.getArgs()[3].equalsIgnoreCase("color")) {
                            if(data.getArgs().length > 4) {
                                try {
                                    jsonEmbed.getEmbedBuilder().setColor(Color.decode(smooshData(4, data.getArgs())));
                                    response.append("You have set the color to `").append(smooshData(4, data.getArgs())).append("`.");
                                } catch (NumberFormatException ex) {
                                    response.append("That is not a valid color!");
                                }
                            } else {
                                jsonEmbed.getEmbedBuilder().setColor(null);
                                response.append("You have cleared the color.");
                            }
                            storage.save();
                        } else {
                            response.append(getPrefix()).append("eb edit (category) (embed) title (text)\n");
                            response.append(getPrefix()).append("eb edit (category) (embed) description (text)\n");
                            response.append(getPrefix()).append("eb edit (category) (embed) footer (text)\n");
                            response.append(getPrefix()).append("eb edit (category) (embed) author (text)\n");
                            response.append(getPrefix()).append("eb edit (category) (embed) url (url)\n");
                            response.append(getPrefix()).append("eb edit (category) (embed) image (image url)\n");
                            response.append(getPrefix()).append("eb edit (category) (embed) color (hex color)\n");
                            response.append("Run the command without an input to remove that parameter.");
                        }
                    } else {
                        response.append("That is not a valid embed.");
                    }
                } else {
                    response.append("That is not a valid category.");
                }
            } else {
                response.append(getPrefix()).append("eb list - List embed categories\n");
                response.append(getPrefix()).append("eb info (category) - View the available embeds\n");
                response.append(getPrefix()).append("eb info (category) (embed) - View the embed\n");
                response.append(getPrefix()).append("eb edit (category) (embed) - Edit the embed\n");
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Embed Manager");
            if(data.get() instanceof MessageReceivedEvent) {
                embedBuilder.setColor(getColor(((MessageReceivedEvent)data.get()).getGuild()));
            }
            embedBuilder.setDescription(response);
            return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
        });
        builder.build();
    }

    public String smooshData(int x, String[] args) {
        StringBuilder builder = new StringBuilder();
        for(int i = x; i < args.length; i++) {
            builder.append(args[i]);
            if(i != args.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

}
