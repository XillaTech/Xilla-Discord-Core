package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.event.BungeeCommandEvent;
import net.xilla.discordcore.command.event.SpigotCommandEvent;

public class CoreCommand extends Command {

    public CoreCommand(String module, String name, String[] activators, String usage, String description, int staffLevel, CommandExecutor executor) {
        super(module, name, activators, usage, description, staffLevel, executor);
    }

    @Override
    public void run(String[] args, String inputType, Object... data) {
        CommandExecutor commandExecutor = getExecutor();
        if(commandExecutor instanceof CoreCommandExecutor) {
            CoreCommandExecutor executor = (CoreCommandExecutor)commandExecutor;

            if(inputType.equalsIgnoreCase(CoreCommandExecutor.bungee_input)) {
                BungeeCommandEvent event = (BungeeCommandEvent)data[0];
                executor.run(getName(), args, inputType, event);
                return;

            } else if(inputType.equalsIgnoreCase(CoreCommandExecutor.spigot_input)) {
                SpigotCommandEvent event = (SpigotCommandEvent)data[0];
                executor.run(getName(), args, inputType, event);
                return;

            } else if(inputType.equalsIgnoreCase(CoreCommandExecutor.discord_input)) {
                MessageReceivedEvent event = (MessageReceivedEvent)data[0];
                executor.run(getName(), args, inputType, event);
                return;
            }
        }

        commandExecutor.run(getName(), args, inputType, data);
    }


}
