package net.xilla.community.giveaway;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.community.CommunityBot;
import net.xilla.community.CommunitySettings;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.form.MultiForm;

public class GiveawayCommands extends CoreObject {

    public GiveawayCommands() {
        CommandBuilder builder = new CommandBuilder("Giveaway", "gstart");
        builder.setDescription("Start a giveaway!");
        builder.setPermission("giveaway.start");
        builder.setCommandExecutor((data) -> {
            MessageReceivedEvent event = (MessageReceivedEvent)data.get();
            if(!CommunitySettings.getInstance().isGiveawayEnabled(event.getGuild().getId())) {
                return null;
            }

            MultiForm form = new MultiForm("Start a Giveaway", event.getTextChannel().getId(), (results) -> {
                try {
                    String item = results.get("Item").getResponse();
                    String emoji = EmojiParser.parseToAliases(results.get("Emoji").getResponse().replace(" ", ""));
                    int amount = Integer.parseInt(results.get("Amount").getResponse());
                    long duration = getTime(results.get("Duration").getResponse());

                    Manager<Giveaway> manager = CommunityBot.getInstance().getGiveawayManager().getManager(event.getGuild());
                    manager.put(new Giveaway(event.getTextChannel(), duration, amount, item, emoji));
                } catch (Exception ex) {
                    event.getTextChannel().sendMessage("Invalid inputs, please try again!").complete();
                    ex.printStackTrace();
                }
            });
            form.addMessageQuestion("Item", "What are you giving away?", event.getAuthor().getId(), event.getGuild().getId());
            form.addMessageQuestion("Amount", "How many are you giving away?", event.getAuthor().getId(), event.getGuild().getId());
            form.addMessageQuestion("Emoji", "What emoji should they react with?", event.getAuthor().getId(), event.getGuild().getId());
            form.addMessageQuestion("Duration", "How long should this giveaway last? (Ex: 3d,3h,30m for 3 days, 3 hours, and 30 minutes)", event.getAuthor().getId(), event.getGuild().getId());

            form.start();
            return null;
        });
        builder.build();
    }

}
