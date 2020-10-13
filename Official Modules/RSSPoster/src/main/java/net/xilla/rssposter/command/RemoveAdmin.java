package net.xilla.rssposter.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.permission.DiscordUser;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.rssposter.server.ServerSettings;
import org.json.simple.JSONArray;

public class RemoveAdmin extends CoreObject {

    public RemoveAdmin() {
        CommandBuilder builder = new CommandBuilder("Core", "RemoveAdmin");
        builder.setDescription("Remove a user from using the admin commands.");
        builder.setUsage("removeadmin (@user)");
        builder.setCommandExecutor((commandData) -> {
            MessageReceivedEvent event = (MessageReceivedEvent)commandData.get();

            ServerSettings serverSettings = ServerSettings.getSettings(event.getGuild());

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(serverSettings.getColor());
            embedBuilder.setFooter("Coded with <3 by Xilla.net");

            boolean isAdmin = event.getMember().hasPermission(Permission.ADMINISTRATOR);
            if(!isAdmin) {
                for(Object obj : serverSettings.getAdmins()) {
                    if(obj.toString().equalsIgnoreCase(event.getAuthor().getId())) {
                        isAdmin = true;
                        break;
                    }
                }
            }

            if(isAdmin) {
                if(commandData.getArgs().length == 1) {
                    Member member = getMember(event.getGuild(), commandData.getArgs()[0]);

                    Object object = null;
                    for(Object obj : serverSettings.getAdmins()) {
                        if(obj.toString().equalsIgnoreCase(member.getId())) {
                            object = obj;
                            break;
                        }
                    }
                    serverSettings.getAdmins().remove(object);
                    serverSettings.save();
                } else {
                    embedBuilder.setDescription(getPrefix() + "removeadmin (@user)");
                }
            } else {
                embedBuilder.setDescription("You do not have permission for this command.");
            }
            return new CoreCommandResponse(commandData).setEmbed(embedBuilder.build());
        });
        builder.build();
    }

}
