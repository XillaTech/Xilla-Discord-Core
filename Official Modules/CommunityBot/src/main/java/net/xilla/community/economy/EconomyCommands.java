package net.xilla.community.economy;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.community.CommunityBot;
import net.xilla.core.library.manager.Manager;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.core.command.response.CoreCommandResponse;

public class EconomyCommands extends CoreObject {

    public EconomyCommands() {
        work();
    }

    public void work() {
        CommandBuilder builder = new CommandBuilder("Economy", "Work");
        builder.setDescription("Claim your credits from work!");
        builder.setCommandExecutor((data -> {
            StringBuilder response = new StringBuilder();

            EmbedBuilder embedBuilder;
            Guild guild = null;
            if(data.get() != null) {
                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                guild = event.getGuild();
                embedBuilder = DiscordCore.getInstance().getServerSettings().get(guild, "core-embed");
            } else {
                embedBuilder = new EmbedBuilder();
            }

            if(guild == null) {
                response.append("This is a discord only command.");
            } else {
                MessageReceivedEvent event = (MessageReceivedEvent)data.get();

                Manager<EconomyUser> manager = CommunityBot.getInstance().getEconomyManager().getManager(guild);

                EconomyUser user = manager.get(event.getAuthor().getId());

                if(user == null) {
                    user = new EconomyUser(event.getAuthor().getId(), guild);
                    manager.put(user);
                    manager.save();
                }

                if(EconomySettings.getInstance().isWorkEnabled(guild)) {
                    long time = user.getLastRedeemedWork();

                    if (time > EconomySettings.getInstance().workDelayMinutes(guild) * 60 * 1000) {
                        int payout = EconomySettings.getInstance().getWorkPayout(guild);
                        String label = EconomySettings.getInstance().getLabel(guild);

                        response.append("You have had a hard day at work, +").append(payout).append(" ").append(label);
                    } else {
                        response.append("You have already worked today!");
                    }

                    user.setLastRedeemedWork(System.currentTimeMillis());
                } else {
                    response.append("Work is not enabled in this server.");
                }
            }

            embedBuilder.setDescription(response.toString());
            return new CoreCommandResponse(data).setEmbed(embedBuilder.build());
        }));
    }

}
