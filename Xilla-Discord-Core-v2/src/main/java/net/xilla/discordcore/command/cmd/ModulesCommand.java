package net.xilla.discordcore.command.cmd;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import com.tobiassteely.tobiasapi.command.response.CommandResponse;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.response.CoreCommandResponder;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.platform.CoreSettings;

public class ModulesCommand extends CoreObject {

    public ModulesCommand() {
        CommandBuilder commandBuilder = new CommandBuilder();
        commandBuilder.setModule("Core");
        commandBuilder.setName("Module");
        commandBuilder.setActivators("modules", "m");
        commandBuilder.setDescription("View and manage your discord modules");
        commandBuilder.setPermission("core.module");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CommandExecutor getExecutor() {
        return (command, args, data) -> {
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

            EmbedBuilder builder = new EmbedBuilder().setTitle("Market");
            builder.setDescription(description);

            return new CoreCommandResponse(data).setEmbed(builder.build());
        };
    }

}
