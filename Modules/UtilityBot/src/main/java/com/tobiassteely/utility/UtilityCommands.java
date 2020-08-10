package com.tobiassteely.utility;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.response.CoreCommandResponse;

import java.awt.*;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UtilityCommands extends CoreObject {

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a").withZone(ZoneOffset.UTC);
    public UtilityCommands() {
        if(UtilityBot.getInstance().getUtilitySettings().isPollsEnabled()) {
            pollCommand();
        }
        if(UtilityBot.getInstance().getUtilitySettings().isPollsEnabled()) {
            serverInfoCommand();
            userInfoCommand();
            roleInfoCommand();
        }
    }

    public void pollCommand() {
        CommandBuilder builder = new CommandBuilder("Utility", "Poll");
        builder.setActivators("poll", "vote");
        builder.setUsage("poll");
        builder.setDescription("Post a poll for your users.");
        builder.setPermission("utility.poll");
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
                Message discordMessage = event.getTextChannel().sendMessage(embedBuilder.build()).complete();
                discordMessage.addReaction(EmojiParser.parseToUnicode(":white_check_mark:")).complete();
                discordMessage.addReaction(EmojiParser.parseToUnicode(":x:")).complete();

                return null;
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }


    public void serverInfoCommand() {
        CommandBuilder builder = new CommandBuilder("Utility", "ServerInfo");
        builder.setDescription("View the server info.");
        builder.setPermission("utility.serverinfo");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle(event.getGuild().getName());

                try {

                    Guild g = event.getGuild();
                    embedBuilder.setThumbnail(g.getIconUrl());

                    embedBuilder.addField("Owner", g.getOwner().getAsMention(), false);
                    embedBuilder.addField("Created At", fmt.format(g.getTimeCreated()) + " UTC", false);

                    int bots = 0;
                    for(Member member : g.getMembers()) {
                        if(member.getUser().isBot()) {
                            bots++;
                        }
                    }
                    int totalUsers = g.getMembers().size();
                    int users = totalUsers - bots;

                    embedBuilder.addField("Member Count", "**Total users** " + totalUsers + "\n**Bots** " + bots + "\n**Real Users** " + users, false);
                    embedBuilder.addField("Region", g.getRegionRaw(), false);

                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 0; i < g.getTextChannels().size(); i++) {
                        TextChannel channel = g.getTextChannels().get(i);
                        stringBuilder.append(channel.getAsMention());
                        if(i != g.getTextChannels().size() - 1) {
                            stringBuilder.append(", ");
                        }
                    }

                    embedBuilder.addField("Text Channels (" + g.getTextChannels().size() + ")", stringBuilder.toString(), false);

                    StringBuilder stringBuilder2 = new StringBuilder();
                    for(int i = 0; i < g.getRoles().size(); i++) {
                        Role role = g.getRoles().get(i);
                        stringBuilder2.append(role.getAsMention());
                        if(i != g.getRoles().size() - 1) {
                            stringBuilder2.append(", ");
                        }
                    }

                    embedBuilder.addField("Roles (" + g.getRoles().size() + ")", stringBuilder2.toString(), false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    embedBuilder.setDescription(ex.getMessage());
                }
                embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
            } else {
                return new CoreCommandResponse(data).setDescription("This is a discord only command.");
            }
        });
        builder.build();
    }

    public void userInfoCommand() {
        CommandBuilder builder = new CommandBuilder("Utility", "UserInfo");
        builder.setDescription("View the user's info.");
        builder.setPermission("utility.userinfo");
        builder.setCommandExecutor((data) -> {
            try {


                if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                    MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("User Info");
                    Guild g = event.getGuild();

                    if(data.getArgs().length == 1) {
                        Member member = event.getGuild().getMemberById(data.getArgs()[0].replace("<@!", "").replace(">", ""));
                        if(member != null) {
                            embedBuilder.setThumbnail(member.getUser().getAvatarUrl());
                            embedBuilder.addField("User", member.getAsMention(), true);
                            embedBuilder.addField("ID", member.getId(), true);
                            embedBuilder.addField("Registered", fmt.format(member.getTimeCreated()) + " UTC", true);
                            embedBuilder.addField("Joined", fmt.format(member.getTimeJoined()) + " UTC", true);

                            StringBuilder stringBuilder = new StringBuilder();
                            for(int i = 0; i < member.getRoles().size(); i++) {
                                Role role = member.getRoles().get(i);
                                stringBuilder.append(role.getAsMention());
                                if(i != member.getRoles().size() - 1) {
                                    stringBuilder.append(", ");
                                }
                            }

                            embedBuilder.addField("Roles", stringBuilder.toString(), true);
                        } else {
                            embedBuilder.setDescription("That is not a valid user!");
                        }
                    } else {
                        embedBuilder.setDescription(getCoreSetting().getCommandPrefix() + "userinfo <@user>");
                    }

                    embedBuilder.setColor(Color.decode(getDiscordCore().getCoreSetting().getEmbedColor()));
                    return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
                } else {
                    return new CoreCommandResponse(data).setDescription("This is a discord only command.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return new CoreCommandResponse(data).setDescription(ex.getMessage());
            }
        });
        builder.build();
    }

    public void roleInfoCommand() {
        CommandBuilder builder = new CommandBuilder("Utility", "RoleInfo");
        builder.setDescription("View the role's info.");
        builder.setPermission("utility.roleinfo");
        builder.setCommandExecutor((data) -> {

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {

                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Role Info");
                Guild g = event.getGuild();

                if(data.getArgs().length == 1) {
                    Role role = event.getGuild().getRoleById(data.getArgs()[0].replace("<@&", "").replace(">", ""));
                    if (role != null) {
                        embedBuilder.addField("Role", role.getAsMention(), true);
                        embedBuilder.addField("ID", "" + role.getId(), true);
                        embedBuilder.addBlankField(true);
                        embedBuilder.addField("Created On", fmt.format(role.getTimeCreated()) + " UTC", true);
                        embedBuilder.addField("Position", "" + role.getPosition(), true);
                        embedBuilder.addBlankField(true);

                        List<Member> members = new ArrayList<>();
                        for(Member member : g.getMembers()) {
                            if(member.getRoles().contains(role)) {
                                members.add(member);
                            }
                        }

                        StringBuilder stringBuilder = new StringBuilder();
                        for(int i = 0; i < members.size(); i++) {
                            Member member = members.get(i);
                            stringBuilder.append(member.getAsMention());

                            if(i != members.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }

                        embedBuilder.addField("Members", stringBuilder.toString(), false);
                    }
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
