package net.xilla.rssposter.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.permission.DiscordUser;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.rssposter.server.ServerSettings;

public class SetChannel {

    public SetChannel() {
        CommandBuilder builder = new CommandBuilder("Core", "SetChannel");
        builder.setDescription("Sets the channel for automated updates.");
        builder.setUsage("setchannel");
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
                serverSettings.set("channel", event.getChannel().getId());
                serverSettings.save();
                embedBuilder.setDescription("You have set this channel up for automatic announcements!");
            } else {
                embedBuilder.setDescription("You do not have permission for this command.");
            }
            return new CoreCommandResponse(commandData).setEmbed(embedBuilder.build());
        });
        builder.build();
    }

}
