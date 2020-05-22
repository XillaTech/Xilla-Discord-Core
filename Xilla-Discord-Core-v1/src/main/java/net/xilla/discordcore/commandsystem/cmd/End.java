package net.xilla.discordcore.commandsystem.cmd;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.staff.StaffManager;
import net.xilla.discordcore.commandsystem.CommandObject;

public class End extends CommandObject {

    public End() {
        super("End", new String[] {"end"}, "Core", "Shuts the bot down", "end", 10);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        if (event == null)
            System.exit(0);
        else {
            if(DiscordCore.getInstance().getStaffManager().isAuthorized(event.getAuthor().getId(), 10)) {
                event.getTextChannel().sendMessage("Shutting down bot...").complete();

                for(Guild guild : DiscordCore.getInstance().getBot().getGuilds())
                    guild.getAudioManager().closeAudioConnection();

                System.exit(0);
            }
        }
        return true;
    }

}
