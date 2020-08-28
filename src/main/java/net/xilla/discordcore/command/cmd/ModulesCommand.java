package net.xilla.discordcore.command.cmd;

import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.response.CoreCommandResponse;

import java.awt.*;

public class ModulesCommand extends CoreObject {

    public ModulesCommand() {
        CommandBuilder commandBuilder = new CommandBuilder("Core", "Module", true);
        commandBuilder.setActivators("module", "modules", "m");
        commandBuilder.setDescription("Get more modules");
        commandBuilder.setPermission("core.module");
        commandBuilder.setCommandExecutor(getExecutor());
        commandBuilder.build();
    }

    public CommandExecutor getExecutor() {
        return (data) -> {
            EmbedBuilder builder = new EmbedBuilder().setTitle("Module Market");
            builder.setDescription("Find more available modules in https://discord.gg/aSKqa5W.");
            builder.setColor(Color.decode(getCoreSetting().getEmbedColor()));

            return new CoreCommandResponse(data).setEmbed(builder.build());
        };
    }

}
