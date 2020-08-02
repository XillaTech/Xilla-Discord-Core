package net.xilla.discordcore.command.cmd;

import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.api.module.Module;

import java.awt.*;

public class ModulesCommand extends CoreObject {

    public ModulesCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Core", "Module", true);
        commandBuilder.setActivators("module", "modules", "m");
        commandBuilder.setDescription("View and manage your discord modules");
        commandBuilder.setPermission("core.module");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CommandExecutor getExecutor() {
        return (data) -> {

            StringBuilder description = new StringBuilder();
            description.append("*Available Commands*\n").append(getCoreSetting().getCommandPrefix())
                    .append("m download <market id> - Download a Module\n").append(getCoreSetting().getCommandPrefix())
                    .append("m info <module name> - Get a modules information\n").append(getCoreSetting().getCommandPrefix())
                    .append("m list - View all modules\n")
                    .append("\n*You can find modules and their market IDs in <https://discord.gg/aSKqa5W>. To delete a module, remove the module from the /modules/ folder. Then restart the bot.*");

            if(data.getArgs().length > 0) {
                if(data.getArgs()[0].equalsIgnoreCase("download")) {

                } else if(data.getArgs()[0].equalsIgnoreCase("list")) {
                    if(getModuleManager().getList().size() == 0) {
                        description = new StringBuilder();
                        description.append("*There are no available modules*");
                    } else {
                        description = new StringBuilder();
                        description.append("*Modules:*\n");
                        for(Object object : getModuleManager().getList()) {
                            Module module = (Module)object;
                            description.append(module.getName()).append(" - ").append(module.getVersion())
                                    .append(" (").append(module.getType()).append(")");
                        }
                    }
                } else if(data.getArgs()[0].equalsIgnoreCase("info")) {

                }
            }
            EmbedBuilder builder = new EmbedBuilder().setTitle("Market");
            builder.setDescription(description);
            builder.setColor(Color.decode(getCoreSetting().getEmbedColor()));

            return new CoreCommandResponse(data).setEmbed(builder.build());
        };
    }

}
