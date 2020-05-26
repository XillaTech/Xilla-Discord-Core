package net.xilla.discordcore.module.cmd;

import com.tobiassteely.tobiasapi.api.Log;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandResponse;
import net.xilla.discordcore.command.type.legacy.LegacyCommand;
import net.xilla.discordcore.platform.CoreSettings;

public class ModulesCommand extends LegacyCommand {

    // String name, String[] activators, String module, String description, String usage, int staffLevel
    public ModulesCommand() {
        super("Modules", new String[] {"modules", "m"}, "Core", "View and manage your modules", "modules", 10);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        Log.sendMessage(0, "0 - Modules");
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
        if(event != null) {
            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Market");
            embedBuilder.setDescription(description);
            new CommandResponse(embedBuilder).send(event.getTextChannel());
        } else {
            new CommandResponse(description).send();
        }
        return true;
    }

}
