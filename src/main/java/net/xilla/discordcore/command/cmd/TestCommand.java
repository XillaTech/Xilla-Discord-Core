package net.xilla.discordcore.command.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.library.embed.menu.MenuItem;
import net.xilla.discordcore.library.embed.menu.type.PaginationMenu;
import net.xilla.discordcore.library.program.ProgramInterface;

import java.util.ArrayList;
import java.util.List;

public class TestCommand implements ProgramInterface {

    public TestCommand() {
        CommandBuilder builder = new CommandBuilder("Core", "Test");
        builder.setPermission("core.test");
        builder.setDescription("Used for admin testing!");
        builder.setCommandExecutor(data -> {

            MessageReceivedEvent event = (MessageReceivedEvent) data.get();

            List<MenuItem> options = new ArrayList<>();

            options.add(new MenuItem("A", ":regional_indicator_symbol_a:", (member, item) -> {
                event.getTextChannel().sendMessage("Option A selected").queue();
            }));
            options.add(new MenuItem("B", ":regional_indicator_symbol_b:", (member, item) -> {
                event.getTextChannel().sendMessage("Option B selected").queue();
            }));
            options.add(new MenuItem("C", ":regional_indicator_symbol_c:", (member, item) -> {
                event.getTextChannel().sendMessage("Option C selected").queue();
            }));
            options.add(new MenuItem("D", ":regional_indicator_symbol_d:", (member, item) -> {
                event.getTextChannel().sendMessage("Option D selected").queue();
            }));
            options.add(new MenuItem("E", ":regional_indicator_symbol_e:", (member, item) -> {
                event.getTextChannel().sendMessage("Option E selected").queue();
            }));
            options.add(new MenuItem("F", ":regional_indicator_symbol_f:", (member, item) -> {
                event.getTextChannel().sendMessage("Option F selected").queue();
            }));
            options.add(new MenuItem("G", ":regional_indicator_symbol_g:", (member, item) -> {
                event.getTextChannel().sendMessage("Option G selected").queue();
            }));

            EmbedBuilder embedBuilder = getEmbed(event.getAuthor().getAsTag(), event.getGuild());
            embedBuilder.setTitle("Example question");
            embedBuilder.setDescription("This is a question where you decide A or B.");

            PaginationMenu menu = new PaginationMenu(embedBuilder, event.getMember(), options, 3);
            menu.send(event.getTextChannel());

            return null;
        });
//        builder.build();
    }

}
