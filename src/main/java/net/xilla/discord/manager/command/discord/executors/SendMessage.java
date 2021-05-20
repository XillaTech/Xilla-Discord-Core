package net.xilla.discord.manager.command.discord.executors;

import lombok.Getter;
import lombok.Setter;
import net.xilla.discord.api.command.CommandExecutor;
import net.xilla.discord.api.command.CommandInput;

public class SendMessage implements CommandExecutor {

    @Getter
    @Setter
    public String message;

    public SendMessage(String message) {
        this.message = message;
    }

    @Override
    public void run(CommandInput input) throws Exception {
        input.getEvent().getChannel().sendMessage(this.message).queue();
    }

}
