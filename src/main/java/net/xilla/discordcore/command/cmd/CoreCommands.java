package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.ObjectInterface;
import net.xilla.core.library.manager.XillaManager;
import net.xilla.discordcore.library.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.server.CoreServer;
import org.json.simple.JSONObject;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CoreCommands implements CoreObject {

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a").withZone(ZoneOffset.UTC);

    public CoreCommands() {
       coreInfo();
    }

    public void coreInfo() {
        CommandBuilder commandBuilder = new CommandBuilder("Core", "CoreInfo", true);
        commandBuilder.setPermission("core.coreinfo");
        commandBuilder.setActivators("coreinfo", "ci");
        commandBuilder.setDescription("Get the discord core's information");
        commandBuilder.setCommandExecutor((data) -> {
            EmbedBuilder embedBuilder = new EmbedBuilder();


            if(data.get() instanceof MessageReceivedEvent) {
                embedBuilder = getEmbed((MessageReceivedEvent)data.get());
            }

            embedBuilder.setTitle("Core Info");

            if(data.getArgs().length > 1 && data.getArgs()[0].equalsIgnoreCase("server")) {
                StringBuilder argument = new StringBuilder();
                for(int i = 1; i < data.getArgs().length; i++) {
                    argument.append(data.getArgs()[i]);
                    if(i != data.getArgs().length - 1) {
                        argument.append(" ");
                    }
                }

                CoreServer coreServer = getPlatform().getServerManager().get(argument.toString());
                if(coreServer != null && coreServer.getGuild() != null) {
                    Guild g = coreServer.getGuild();
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
                    embedBuilder.addField("Bot Statistics", "", false);
                } else {
                    embedBuilder.setDescription("That is not a valid server!");
                }

            } else if(data.getArgs().length >= 3 && data.getArgs()[0].equalsIgnoreCase("object")) {

                StringBuilder argument = new StringBuilder();
                for(int i = 2; i < data.getArgs().length; i++) {
                    argument.append(data.getArgs()[i]);
                    if(i != data.getArgs().length - 1) {
                        argument.append(" ");
                    }
                }

                Manager manager = XillaManager.getInstance().get(data.getArgs()[1]);
                if(manager != null) {
                    ObjectInterface object = manager.get(argument.toString());
                    if(object != null) {
                        if(object.getSerializedData() != null) {
                            embedBuilder.setDescription("Object Data: ```" + formatJSONStr(object.getSerializedData().getJson()) + "```");
                        } else {
                            embedBuilder.setDescription("Object Data: None");
                        }
                    } else {
                        embedBuilder.setDescription("That is not a valid object!");
                    }
                } else {
                    embedBuilder.setDescription("That is not a valid manager!");
                }
            } else if(data.getArgs().length > 1 && data.getArgs()[0].equalsIgnoreCase("manager")) {

                StringBuilder argument = new StringBuilder();
                for(int i = 1; i < data.getArgs().length; i++) {
                    argument.append(data.getArgs()[i]);
                    if(i != data.getArgs().length - 1) {
                        argument.append(" ");
                    }
                }

                Manager manager = XillaManager.getInstance().get(argument.toString());
                if(manager != null) {
                    StringBuilder str = new StringBuilder();

                    List<ManagerObject> objects = new ArrayList<>(manager.getData().values());

                    int loop = 0;
                    for(ManagerObject object : objects) {
                        str.append(object.getKey());
                        loop++;
                        if(loop != objects.size()) {
                            str.append(", ");
                        }
                    }
                    embedBuilder.setDescription("Objects (" + objects.size() + "): " + str.toString());
                } else {
                    embedBuilder.setDescription("That is not a valid manager!");
                }


            } else {
                StringBuilder stb = new StringBuilder();
                int sloop = 0;
                for (CoreServer server : new ArrayList<>(getDiscordCore().getPlatform().getServerManager().getData().values())) {
                    if (server.getGuild() != null) {
                        stb.append(server.getGuild().getName()).append(" (ID: ").append(server.getKey()).append(")");
                    } else {
                        stb.append("N/A").append(" (ID: ").append(server.getKey()).append(")");
                    }
                    sloop++;
                    if(sloop != getDiscordCore().getPlatform().getServerManager().getData().size()) {
                        stb.append(", ");
                    }
                }

                StringBuilder mtb = new StringBuilder();
                int mloop = 0;
                for (Manager manager : XillaManager.getInstance().iterate()) {
                    mtb.append("> ").append(manager.getName()).append(" (").append(manager.getData().size()).append(")");
                    mloop++;
                    if (mloop != XillaManager.getInstance().getData().size()) {
                        mtb.append("\n");
                    }
                }
                if(getDiscordCore().getPlatform().getServerManager().getData().size() > 0) {
                    embedBuilder.setDescription("Servers (" + getDiscordCore().getPlatform().getServerManager().iterate() + "): `" + stb.toString()
                            + "`\n\nManagers (" + XillaManager.getInstance().iterate().size()
                            + "): \n" + mtb.toString() + "\n\n" + getPrefix() + "ci server (server name)\n"
                            + getPrefix() + "ci manager (manager name)\n" + getPrefix() + "ci object (manager name) (object name)");
                } else {
                    embedBuilder.setDescription("Servers (0): None\n\nManagers (" + XillaManager.getInstance().iterate().size()
                            + "): \n" + mtb.toString() + "\n\n" + getPrefix() + "ci server (server name)\n"
                            + getPrefix() + "ci manager (manager name)\n" + getPrefix() + "ci object (manager name) (object name)");
                }
            }

            return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
        });
        commandBuilder.build();
    }

    private String formatJSONStr(JSONObject json) {
        final String json_str = json.toJSONString();
        final int indent_width = 1;
        final char[] chars = json_str.toCharArray();
        final String newline = System.lineSeparator();

        String ret = "";
        boolean begin_quotes = false;

        for (int i = 0, indent = 0; i < chars.length; i++) {
            char c = chars[i];

            if (c == '\"') {
                ret += c;
                begin_quotes = !begin_quotes;
                continue;
            }

            if (!begin_quotes) {
                switch (c) {
                    case '{':
                    case '[':
                        ret += c + newline + String.format("%" + (indent += indent_width) + "s", "");
                        continue;
                    case '}':
                    case ']':
                        ret += newline + ((indent -= indent_width) > 0 ? String.format("%" + indent + "s", "") : "") + c;
                        continue;
                    case ':':
                        ret += c + " ";
                        continue;
                    case ',':
                        ret += c + newline + (indent > 0 ? String.format("%" + indent + "s", "") : "");
                        continue;
                    default:
                        if (Character.isWhitespace(c)) continue;
                }
            }

            ret += c + (c == '\\' ? "" + chars[++i] : "");
        }

        return ret;
    }

}
