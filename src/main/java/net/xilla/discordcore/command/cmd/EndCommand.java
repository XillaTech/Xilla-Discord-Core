package net.xilla.discordcore.command.cmd;

import com.tobiassteely.tobiasapi.command.Command;
import com.tobiassteely.tobiasapi.command.CommandExecutor;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.response.CoreCommandResponse;

import java.util.ArrayList;

public class EndCommand extends CoreObject {

    public EndCommand() {
        Command command = getCommandManager().getCommand("End");

        ArrayList<CommandExecutor> executors = new ArrayList<>();
        executors.add((data) -> {
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

        command.setExecutors(executors);
    }

}
