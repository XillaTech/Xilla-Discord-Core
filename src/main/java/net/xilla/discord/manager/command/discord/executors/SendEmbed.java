package net.xilla.discord.manager.command.discord.executors;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discord.api.command.CommandExecutor;
import net.xilla.discord.api.command.CommandInput;

public class SendEmbed implements CommandExecutor {

    @Getter
    @Setter
    public EmbedBuilder embedBuilder;

    public SendEmbed(EmbedBuilder embedBuilder) {
        this.embedBuilder = embedBuilder;
    }

    @Override
    public void run(CommandInput input) throws Exception {
        input.getEvent().getChannel().sendMessage(this.embedBuilder.build()).queue();
    }

}
