package com.tobiassteely.utility.punishment;

import com.tobiassteely.utility.UtilityBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.command.CommandData;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public class PunishmentCommands extends CoreObject {

    public PunishmentCommands() {
        banCommand();
        tempbanCommand();
        unbanCommand();
        muteCommand();
        tempmuteCommand();
        unMuteCommand();
        warnCommand();
        removewarnCommand();
        historyCommand();
        kickCommand();
        clearCommand();
    }

    public void banCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "Ban");
        builder.setDescription("Ban a user.");
        builder.setPermission("utility.ban");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                User user = null;
                if(data.getArgs().length >= 1) {
                    user = getBot().getUserById(data.getArgs()[0].replace("<@!", "").replace(">", ""));
                } else {
                    embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "ban <@user> (reason)");
                }

                if(user != null) {
                    String description = getDescription(data, 1);
                    Punishment punishment = createPunishment(event.getAuthor().getId(), user.getId(),"Ban", event.getGuild().getId(), description, -1);
                    embedBuilder.setDescription("You have banned " + user.getAsMention() + " for `" + description + "`.");
                    embedBuilder.setFooter("Punishment ID: `" + punishment.getKey() + "`");

                    EmbedBuilder punishmentEmbed = new EmbedBuilder().setTitle("Punishment");
                    punishmentEmbed.setDescription("You've been banned from " + event.getGuild().getName() + " for `" + description + "`");
                    punishmentEmbed.setFooter("Punishment ID: `" + punishment.getKey() + "`");
                    punishmentEmbed.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                    user.openPrivateChannel().complete().sendMessage(punishmentEmbed.build()).complete();

                    event.getGuild().ban(user, 0).queue();
                } else {
                    embedBuilder.setDescription("That is not a valid user!");
                }

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void tempbanCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "TempBan");
        builder.setDescription("Temp Ban a user.");
        builder.setPermission("utility.tempban");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                User user = null;
                if(data.getArgs().length >= 2) {
                    user = getBot().getUserById(data.getArgs()[0].replace("<@!", "").replace(">", ""));
                } else {
                    embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "tempban <@user> <days> (reason)");
                }

                if(user != null) {
                    String description = getDescription(data, 2);
                    try {
                        int days = Integer.parseInt(data.getArgs()[1]);
                        Punishment punishment = createPunishment(event.getAuthor().getId(), user.getId(),"Ban", event.getGuild().getId(), description, 86000 * days);
                        embedBuilder.setDescription("You have temporarily banned " + user.getAsMention() + " for `" + description + "`.");
                        embedBuilder.setFooter("Punishment ID: `" + punishment.getKey() + "`");

                        EmbedBuilder punishmentEmbed = new EmbedBuilder().setTitle("Punishment");
                        punishmentEmbed.setDescription("You've been temporarily banned from " + event.getGuild().getName() + " for `" + description + "`");
                        punishmentEmbed.setFooter("Punishment ID: `" + punishment.getKey() + "`");
                        punishmentEmbed.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                        user.openPrivateChannel().complete().sendMessage(punishmentEmbed.build()).complete();
                        event.getGuild().ban(user, days).queue();
                    } catch (Exception ex) {
                        embedBuilder.setDescription("tempban <@user> <days> (reason)");
                    }
                } else {
                    embedBuilder.setDescription("That is not a valid user!");
                }

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void unbanCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "Unban");
        builder.setDescription("Unban a user.");
        builder.setPermission("utility.unban");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                User user = null;
                if(data.getArgs().length >= 1) {
                    user = getBot().getUserById(data.getArgs()[0].replace("<@!", "").replace(">", ""));
                } else {
                    embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "unban <@user> (reason)");
                }

                if(user != null) {
                    String description = getDescription(data, 1);
                    Punishment punishment = createPunishment(event.getAuthor().getId(), user.getId(),"Unban", event.getGuild().getId(), description, -1);
                    embedBuilder.setDescription("You have unbanned " + user.getAsMention() + "!");
                    embedBuilder.setFooter("Punishment ID: `" + punishment.getKey() + "`");
                    event.getGuild().unban(user).queue();
                } else {
                    embedBuilder.setDescription("That is not a valid user!");
                }

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void muteCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "Mute");
        builder.setDescription("Mute a user.");
        builder.setPermission("utility.mute");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                User user = null;
                if(data.getArgs().length >= 1) {
                    user = getBot().getUserById(data.getArgs()[0].replace("<@!", "").replace(">", ""));
                } else {
                    embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "mute <@user> (reason)");
                }

                if(user != null) {
                    String description = getDescription(data, 1);
                    Punishment punishment = createPunishment(event.getAuthor().getId(), user.getId(),"Mute", event.getGuild().getId(), description, -1);
                    embedBuilder.setDescription("You have muted " + user.getAsMention() + " for `" + description + "`.");
                    embedBuilder.setFooter("Punishment ID: `" + punishment.getKey() + "`");

                    EmbedBuilder punishmentEmbed = new EmbedBuilder().setTitle("Punishment");
                    punishmentEmbed.setDescription("You've been muted in " + event.getGuild().getName() + " for `" + description + "`");
                    punishmentEmbed.setFooter("Punishment ID: `" + punishment.getKey() + "`");
                    punishmentEmbed.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                    user.openPrivateChannel().complete().sendMessage(punishmentEmbed.build()).complete();
                } else {
                    embedBuilder.setDescription("That is not a valid user!");
                }

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void tempmuteCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "TempMute");
        builder.setDescription("Temp Mute a user.");
        builder.setPermission("utility.tempmute");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                User user = null;
                if(data.getArgs().length >= 2) {
                    user = getBot().getUserById(data.getArgs()[0].replace("<@!", "").replace(">", ""));
                } else {
                    embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "tempmute <@user> <minutes> (reason)");
                }

                if(user != null) {
                    String description = getDescription(data, 2);
                    try {
                        int minutes = Integer.parseInt(data.getArgs()[1]);
                        Punishment punishment = createPunishment(event.getAuthor().getId(), user.getId(),"Mute", event.getGuild().getId(), description, minutes);
                        embedBuilder.setDescription("You have temporarily muted " + user.getAsMention() + " for `" + description + "`.");
                        embedBuilder.setFooter("Punishment ID: `" + punishment.getKey() + "`");

                        EmbedBuilder punishmentEmbed = new EmbedBuilder().setTitle("Punishment");
                        punishmentEmbed.setDescription("You've been temporarily muted in " + event.getGuild().getName() + " for `" + description + "`");
                        punishmentEmbed.setFooter("Punishment ID: `" + punishment.getKey() + "`");
                        punishmentEmbed.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                        user.openPrivateChannel().complete().sendMessage(punishmentEmbed.build()).complete();
                    } catch (Exception ex) {
                        embedBuilder.setDescription("tempmute <@user> <minutes> (reason)");
                    }
                } else {
                    embedBuilder.setDescription("That is not a valid user!");
                }

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void unMuteCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "Unmute");
        builder.setDescription("Unmute a user.");
        builder.setPermission("utility.unmute");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void warnCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "Warn");
        builder.setDescription("Warn a user.");
        builder.setPermission("utility.warn");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                User user = null;
                if(data.getArgs().length >= 1) {
                    user = getBot().getUserById(data.getArgs()[0].replace("<@!", "").replace(">", ""));
                } else {
                    embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "warn <@user> (reason)");
                }

                if(user != null) {
                    String description = getDescription(data, 1);
                    Punishment punishment = createPunishment(event.getAuthor().getId(), user.getId(),"Warn", event.getGuild().getId(),description, -1);
                    embedBuilder.setDescription("You have warned " + user.getAsMention() + " for `" + description + "`.");
                    embedBuilder.setFooter("Punishment ID: `" + punishment.getKey() + "`");

                    EmbedBuilder punishmentEmbed = new EmbedBuilder().setTitle("Punishment");
                    punishmentEmbed.setDescription("You've been warned in " + event.getGuild().getName() + " for `" + description + "`");
                    punishmentEmbed.setFooter("Punishment ID: `" + punishment.getKey() + "`");
                    punishmentEmbed.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                    user.openPrivateChannel().complete().sendMessage(punishmentEmbed.build()).complete();
                } else {
                    embedBuilder.setDescription("That is not a valid user!");
                }


                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void removewarnCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "RemoveWarn");
        builder.setDescription("Remove a warn");
        builder.setPermission("utility.removewarn");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void historyCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "History");
        builder.setActivators("history", "punishments");
        builder.setDescription("View a users punishment history.");
        builder.setPermission("utility.warn");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void kickCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "Kick");
        builder.setDescription("Kick a user.");
        builder.setPermission("utility.kick");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                User user = null;
                if(data.getArgs().length >= 1) {
                    user = getBot().getUserById(data.getArgs()[0].replace("<@!", "").replace(">", ""));
                } else {
                    embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "kick <@user> (reason)");
                }

                if(user != null) {
                    String description = getDescription(data, 1);
                    Punishment punishment = createPunishment(event.getAuthor().getId(), user.getId(),"Kick", event.getGuild().getId(), description, -1);
                    embedBuilder.setDescription("You have kicked " + user.getAsMention() + " for `" + description + "`.");
                    embedBuilder.setFooter("Punishment ID: `" + punishment.getKey() + "`");

                    EmbedBuilder punishmentEmbed = new EmbedBuilder().setTitle("Punishment");
                    punishmentEmbed.setDescription("You've been kicked in " + event.getGuild().getName() + " for `" + description + "`");
                    punishmentEmbed.setFooter("Punishment ID: `" + punishment.getKey() + "`");
                    punishmentEmbed.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                    user.openPrivateChannel().complete().sendMessage(punishmentEmbed.build()).complete();

                    event.getGuild().kick(user.getId()).queue();
                } else {
                    embedBuilder.setDescription("That is not a valid user!");
                }

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void clearCommand() {
        CommandBuilder builder = new CommandBuilder("Moderation", "Purge");
        builder.setActivators("purge", "clear");
        builder.setDescription("Purge a channel.");
        builder.setPermission("utility.purge");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();

                if(data.getArgs().length == 1) {
                    try {
                        int messages = Integer.parseInt(data.getArgs()[0]);
                        if(messages > 100) {
                            int start = messages;
                            for (int i = 1; i <= messages / 100 + 1; i++) {
                                int amount = 100;
                                if (i == messages / 100 + 1) {
                                    amount = start;
                                }

                                start -= 100;

                                List<Message> discordMessages = event.getChannel().getHistory().retrievePast(amount).complete();
                                event.getChannel().purgeMessages(discordMessages);
                            }
                        } else {
                            List<Message> discordMessages = event.getChannel().getHistory().retrievePast(messages).complete();
                            event.getChannel().purgeMessages(discordMessages);
                        }
                        embedBuilder.setDescription("Cleared " + messages + " messages!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "purge <# of messages>");
                    }
                } else {
                    embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "purge <# of messages>");
                }

                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public User getUser(String id) {
        return getDiscordCore().getBot().getUserById(id);
    }

    public String getDescription(CommandData data, int start) {
        StringBuilder description = new StringBuilder();
        description.append("No description provided");
        if(data.getArgs().length > start) {
            description = new StringBuilder();
            for(int i = start; i < data.getArgs().length; i++) {
                description.append(data.getArgs()[i]);

                if(i != data.getArgs().length - 1) {

                }
            }
        }
        return description.toString();
    }

    //String id, String staffID, String userID, String type, long startTime, long duration
    public Punishment createPunishment(String staffID, String userID, String type, String guildID, String description, long duration) {

        String id = UUID.randomUUID().toString();

        while(UtilityBot.getInstance().getPunishmentManager().get(id) != null) {
            id = UUID.randomUUID().toString();
        }

        Punishment punishment = new Punishment(id, staffID, userID, type, guildID, description,System.currentTimeMillis(), duration * 60);

        UtilityBot.getInstance().getPunishmentManager().put(punishment);
        UtilityBot.getInstance().getPunishmentManager().save();
        return punishment;
    }
}
