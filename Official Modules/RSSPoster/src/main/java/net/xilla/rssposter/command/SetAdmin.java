package net.xilla.rssposter.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.permission.DiscordUser;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.core.command.CommandExecutor;
import net.xilla.discordcore.core.command.response.CommandResponse;
import net.xilla.rssposter.server.ServerSettings;

public class SetAdmin extends CoreObject {

    public SetAdmin() {
        CommandBuilder builder = new CommandBuilder("Core", "SetAdmin");
        builder.setDescription("Allow a user to use the admin commands.");
        builder.setUsage("setadmin (@user)");
        builder.setCommandExecutor((commandData) -> {
            MessageReceivedEvent event = (MessageReceivedEvent)commandData.get();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(ServerSettings.getSettings(event.getGuild()).getColor());
            embedBuilder.setFooter("Coded with <3 by Xilla.net");

            ServerSettings serverSettings = ServerSettings.getSettings(event.getGuild());

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

                    serverSettings.getAdmins().add(member.getId());
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
