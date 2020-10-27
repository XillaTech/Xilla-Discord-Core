package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.template.type.EmbedCommand;
import net.xilla.discordcore.command.template.type.TextCommand;
import net.xilla.discordcore.core.CoreCommandExecutor;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.form.MultiForm;

import java.util.Date;

public class TemplateCommand extends CoreObject {

    public TemplateCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Core", "TemplateManager", false);
        commandBuilder.setActivators("template", "templatemanager", "tm");
        commandBuilder.setUsage("template");
        commandBuilder.setDescription("View and manage your template commands");
        commandBuilder.setPermission("core.builder");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CoreCommandExecutor getExecutor() {
        return (data) -> {

            StringBuilder description = new StringBuilder();
            description.append("*Available Commands*\n");
            description.append(getCoreSetting().getCommandPrefix()).append("tm list - List available template commands\n");
            description.append(getCoreSetting().getCommandPrefix()).append("tm delete <name> - Delete a template command\n");
            description.append(getCoreSetting().getCommandPrefix()).append("tm info <name> - View a template command\n");
            description.append(getCoreSetting().getCommandPrefix()).append("tm create - View a template command\n");

            if(data.get() instanceof MessageReceivedEvent) {
                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                if (data.getArgs().length > 0 && data.getArgs()[0].equalsIgnoreCase("list")) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();

                    if(data.get() instanceof MessageReceivedEvent) {
                        embedBuilder = getEmbed((MessageReceivedEvent)data.get());
                    }

                    embedBuilder.setTitle("Template");

                    if(getPlatform().getTemplateManager().getCommands().getCache().keySet().size() == 0) {
                        embedBuilder.setDescription("There are no valid commands.");
                    } else {
                        String commands = "";
                        int loop = 0;
                        for(Object obj : getPlatform().getTemplateManager().getCommands().getCache().keySet()) {
                            loop++;
                            commands = commands + obj;
                            if(loop != getPlatform().getTemplateManager().getCommands().getCache().keySet().size()) {
                                commands = commands + "\n";
                            }
                        }
                        embedBuilder.setDescription("*Commands*\n```" + commands + "```");
                    }

                    return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
                } else if (data.getArgs().length > 1 && data.getArgs()[0].equalsIgnoreCase("delete")) {
                    net.xilla.discordcore.command.template.TemplateCommand command = getPlatform().getTemplateManager().getTemplateCommand(data.getArgs()[1]);

                    EmbedBuilder embedBuilder = new EmbedBuilder();

                    if(data.get() instanceof MessageReceivedEvent) {
                        embedBuilder = getEmbed((MessageReceivedEvent)data.get());
                    }

                    MessageEmbed embed = embedBuilder.build();
                    if(embed.getFooter() != null && embed.getFooter().getText() != null) {
                        embedBuilder.setFooter(embed.getFooter().getText().replace("%date%", new Date().toString()));
                        embedBuilder.setFooter(embed.getFooter().getText().replace("%user%", event.getAuthor().getAsMention()));
                    }
                    embedBuilder.setColor(getColor(((MessageReceivedEvent)data.get()).getGuild()));

                    embedBuilder.setTitle("Template");
                    if(command != null) {
                        getPlatform().getTemplateManager().remove(data.getArgs()[1]);
                        getPlatform().getTemplateManager().save();
                        DiscordCore.getInstance().getCommandManager().remove(data.getArgs()[1]);
                        embedBuilder.setDescription("You have deleted that command!");
                    } else {
                        embedBuilder.setDescription("That is not a valid command");
                    }

                    return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
                } else if (data.getArgs().length > 1 && data.getArgs()[0].equalsIgnoreCase("info")) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    if(data.get() instanceof MessageReceivedEvent) {
                        embedBuilder = getEmbed((MessageReceivedEvent)data.get());
                    }
                    embedBuilder.setTitle("Template");

                    net.xilla.discordcore.command.template.TemplateCommand command = getPlatform().getTemplateManager().getTemplateCommand(data.getArgs()[1]);
                    if(command != null) {
                        embedBuilder.setDescription("*Command Name*\n```" + command.getName() + "```\n"
                                + "*Description*\n```" + command.getDescription() + "```\n"
                                + "*Module*\n```" + command.getModule() + "```\n"
                                + "*Permission*\n```" + command.getPermission() + "```\n");
                    } else {
                        embedBuilder.setDescription("That is not a valid command");
                    }

                    return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
                } else if (data.getArgs().length > 0 && data.getArgs()[0].equalsIgnoreCase("create")) {
                    MultiForm form = new MultiForm("Template", event.getTextChannel().getId(), (results) -> {
                        try {
                            boolean embed = Boolean.parseBoolean(results.get("Embed").getResponse());
                            String permission = results.get("Permission").getResponse();
                            if(permission.equalsIgnoreCase("none")) {
                                permission = null;
                            }
                            String module = results.get("Module").getResponse();
                            String commandName = results.get("Name").getResponse();
                            String commandDescription = results.get("Description").getResponse();
                            String response = results.get("Response").getResponse();
                            if(embed) {
                                //String module, String name, String[] activators, String description, String usage, String title, String text, String permission
                                EmbedCommand command = new EmbedCommand(module, commandName, new String[] {commandName.toLowerCase()}, commandDescription, commandName.toLowerCase(), commandName, response, permission);
                                getPlatform().getTemplateManager().registerTemplate(command);
                            } else {
                                //String module, String name, String[] activators, String description, String usage, String text, String permission
                                TextCommand command = new TextCommand(module, commandName, new String[] {commandName.toLowerCase()}, commandDescription, commandName.toLowerCase(), response, permission);
                                getPlatform().getTemplateManager().registerTemplate(command);
                            }
                            getPlatform().getTemplateManager().save();
                            event.getChannel().sendMessage("The command has been successfully added. Do `" + getDiscordCore().getSettings().getCommandPrefix() + "help`").queue();
                        } catch (Exception ex) {
                            event.getChannel().sendMessage("Invalid input, please start over. ```" + ex.getMessage() + "```").queue();
                        }
                    });
                    form.addMessageQuestion("Name", "What is the name of the command you'd like to add?", event.getAuthor().getId(), event.getGuild().getId());
                    form.addMessageQuestion("Description", "What is the description of the command you'd like to add?", event.getAuthor().getId(), event.getGuild().getId());
                    form.addMessageQuestion("Response", "What would you like the command to say?", event.getAuthor().getId(), event.getGuild().getId());
                    form.addMessageQuestion("Module", "What module would you like the command to be under?", event.getAuthor().getId(), event.getGuild().getId());
                    form.addMessageQuestion("Permission", "Would you like to require a permission? (Put \"None\" for no permissions)", event.getAuthor().getId(), event.getGuild().getId());
                    form.addMessageQuestion("Embed", "Would you like the response to be in an embed? (True/False)", event.getAuthor().getId(), event.getGuild().getId());
                    form.start();

                    return new CoreCommandResponse(data);
                }
            }

            EmbedBuilder builder = new EmbedBuilder();

            if(data.get() instanceof MessageReceivedEvent) {
                builder = getEmbed((MessageReceivedEvent)data.get());
            }

            builder.setTitle("Template Manager");
            builder.setDescription(description.toString());

            return new CoreCommandResponse(data).setEmbed(builder.build());
        };
    }
}