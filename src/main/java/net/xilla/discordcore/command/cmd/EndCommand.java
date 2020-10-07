package net.xilla.discordcore.command.cmd;

import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.core.command.Command;
import net.xilla.discordcore.core.command.CommandExecutor;

import java.util.ArrayList;

public class EndCommand extends CoreObject {

    public EndCommand() {
        CommandBuilder builder = new CommandBuilder("Core", "End");
        builder.setDescription("Shutdown the bot.");
        builder.setPermission("core.end");

        builder.setCommandExecutor((data) -> {
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getDiscordCore().shutdown();
                System.exit(0);
            }).start();
            return new CoreCommandResponse(data).setDescription("Shutting down the bot in 3 seconds!");
        });

        builder.build();
    }

}
