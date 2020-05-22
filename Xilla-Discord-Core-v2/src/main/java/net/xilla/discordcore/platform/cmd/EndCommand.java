package net.xilla.discordcore.platform.cmd;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.Command;

public class EndCommand extends Command {

    public EndCommand() {
        super("End", new String[] {"end"}, "Core", "Shuts the bot down", "end", 10);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        if (event == null)
            System.exit(0);
        else {
            event.getTextChannel().sendMessage("Shutting down bot...").complete();

            for(Guild guild : DiscordCore.getInstance().getBot().getGuilds())
                guild.getAudioManager().closeAudioConnection();

            System.exit(0);
        }
        return true;
    }
}
