package net.xilla.discordcore.module.cmd;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CoreCommandResponse;
import net.xilla.discordcore.platform.CoreSettings;

public class ModulesCommand extends TobiasObject {

    private CommandExecutor executor;

    public ModulesCommand() {
        this.executor = (command, args, inputType, data) -> {
            MessageReceivedEvent event = (MessageReceivedEvent)data[0];
            if(args.length > 0) {
                if(args[0].equalsIgnoreCase("download")) {

                } else if(args[0].equalsIgnoreCase("list")) {

                } else if(args[0].equalsIgnoreCase("info")) {

                }
            }

            CoreSettings settings = DiscordCore.getInstance().getSettings();
            String description = "*Available Commands*\n"
                    + settings.getCommandPrefix() + "m download <market id> - Download a Module\n"
                    + settings.getCommandPrefix() + "m info <module name> - Get a modules information\n"
                    + settings.getCommandPrefix() + "m list - View all modules\n"
                    + "\n*You can find modules and their market IDs in <https://discord.gg/aSKqa5W>. To delete a module, turn the plugin off and remove it from the /modules/ folder. Then turn the bot back on.*";

            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Market");
            embedBuilder.setDescription(description);

            CoreCommandResponse response = (CoreCommandResponse)getCommandManager().getResponse();
            response.send(embedBuilder, inputType);
        };
    }

    public Command build() {
        return new Command("Core", "Modules", new String[] {"modules", "m"}, "modules", "View and manage your discord modules", 10, executor);
    }

}
