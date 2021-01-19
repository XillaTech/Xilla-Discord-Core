package net.xilla.discordcore.command.cmd;

import net.xilla.discordcore.library.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;

public class RestartCommand implements CoreObject {

    public RestartCommand() {
        restartCommand();
    }

    public void restartCommand() {
        CommandBuilder builder = new CommandBuilder("Core", "Restart");
        builder.setDescription("Perform a soft-restart on the bot.");
        builder.setPermission("core.restart");
        builder.setCommandExecutor((data) -> {
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getDiscordCore().restart();
            }).start();
            return new CoreCommandResponse(data).setDescription("Restarting the bot in 3 seconds! Please keep in mind, this does not update the core.");
        });
        builder.build();
    }

}
