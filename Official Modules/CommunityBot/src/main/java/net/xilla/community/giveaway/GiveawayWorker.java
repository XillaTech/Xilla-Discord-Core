package net.xilla.community.giveaway;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.community.CommunityBot;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.worker.Worker;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.embed.JSONEmbed;

import java.util.ArrayList;
import java.util.Random;

public class GiveawayWorker extends Worker {

    public GiveawayWorker() {
        super("Giveaways", 15000);
    }

    @Override
    public void runWorker(long l) {
        for(Manager<Giveaway> manager : CommunityBot.getInstance().getGiveawayManager().getManagers().values()) {
            for(Giveaway giveaway : new ArrayList<>(manager.getData().values())) {
                if(giveaway.isActive()) {
                    if(System.currentTimeMillis() > giveaway.getStartTime() + giveaway.getDuration()) {
                        Random random = new Random();

                        String winner;
                        if(giveaway.getUsers().size() != 0) {
                            String winnerID = giveaway.getUsers().get(random.nextInt(giveaway.getUsers().size()));
                            winner = "<@!" + winnerID + ">";
                        } else {
                            winner = "No one";
                        }

                        TextChannel channel = DiscordAPI.getBot().getTextChannelById(giveaway.getChannelID());
                        if (channel != null) {
                            Message message = channel.retrieveMessageById(giveaway.getKey()).complete();
                            if (message != null) {

                                JSONEmbed embed = GiveawaySettings.getInstance().getFinishedEmbed(channel.getGuild());

                                String title = embed.getEmbedBuilder().build().getTitle();
                                String description = embed.getEmbedBuilder().build().getDescription();

                                String footer = null;
                                if (embed.getEmbedBuilder().build().getFooter() != null) {
                                    footer = embed.getEmbedBuilder().build().getFooter().getText();
                                }

                                if (title != null) {
                                    embed.getEmbedBuilder().setTitle(giveaway.injectPlaceholders(title.replace("%winner%", winner)));
                                }

                                if (description != null) {
                                    embed.getEmbedBuilder().setDescription(giveaway.injectPlaceholders(description.replace("%winner%", winner)));
                                }

                                if (footer != null) {
                                    embed.getEmbedBuilder().setFooter(giveaway.injectPlaceholders(footer.replace("%winner%", winner)));
                                }
                                message.editMessage(embed.build()).queue();
                            }
                            if (giveaway.getUsers().size() != 0) {
                                channel.sendMessage(winner + " has won the giveaway for " + giveaway.getAmount() + "x `" + giveaway.getName() + "`!").queue();
                            }
                        }
                        giveaway.setActive(false);
                    } else {
                        TextChannel channel = DiscordAPI.getBot().getTextChannelById(giveaway.getChannelID());
                        if (channel != null) {
                            Message message = channel.retrieveMessageById(giveaway.getKey()).complete();
                            if (message != null) {

                                JSONEmbed embed = GiveawaySettings.getInstance().getEmbed(channel.getGuild());

                                String title = embed.getEmbedBuilder().build().getTitle();
                                String description = embed.getEmbedBuilder().build().getDescription();

                                String footer = null;
                                if (embed.getEmbedBuilder().build().getFooter() != null) {
                                    footer = embed.getEmbedBuilder().build().getFooter().getText();
                                }

                                if (title != null) {
                                    embed.getEmbedBuilder().setTitle(giveaway.injectPlaceholders(title));
                                }

                                if (description != null) {
                                    embed.getEmbedBuilder().setDescription(giveaway.injectPlaceholders(description));
                                }

                                if (footer != null) {
                                    embed.getEmbedBuilder().setFooter(giveaway.injectPlaceholders(footer));
                                }
                                message.editMessage(embed.build()).queue();
                            }
                        }
                    }
                }
            }
        }
    }

}
