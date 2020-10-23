package net.xilla.rssposter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.core.library.config.Config;
import net.xilla.core.library.worker.Worker;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;
import net.xilla.rssposter.post.Feed;
import net.xilla.rssposter.post.FeedMessage;
import net.xilla.rssposter.post.RSSFeedParser;
import net.xilla.rssposter.server.ServerSettings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RSSWorker extends Worker {

    private String name;
    private String link;
    private RSSFeedParser parser;
    private Config config;

    public RSSWorker(String name, String link, long frequency) {
        super("Feed-" + name, frequency);
        Logger.log(LogLevel.DEBUG, "Loading the RSS worker", getClass());

        this.link = link;
        this.parser = new RSSFeedParser(link);
        this.config = new Config("feeds/" + name + ".json");
        this.name = name;
    }

    @Override
    public void runWorker(long l) {
        try {
            Logger.log(LogLevel.DEBUG, "Running the RSS worker", getClass());
            Feed feed = parser.readFeed();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setFooter("Coded with <3 by Xilla.net");

            int max = RSSPoster.getInstance().getRssSettings().getMaxLength();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm ZZ");

            String dateString = format.format(new Date());

            for(FeedMessage feedMessage : feed.getMessages()) {
                if(!config.getJson().containsKey(feedMessage.getGuid())) {
                    config.set(feedMessage.getGuid(), feedMessage.getSerializedData().getJson());

                    embedBuilder.setTitle(feedMessage.getTitle());

                    String description = feedMessage.getDescription();
                    description = description.replaceAll("\\<[^>]*>", "");

                    if (description.length() > max) {
                        description = description.substring(0, max - 2) + "... [Click here to read more!](" + feedMessage.getLink() + ")";
                    }

                    embedBuilder.setDescription(description);
                    embedBuilder.setAuthor(name + " (Pulled " + dateString + ")", feedMessage.getLink());

                    for (Guild guild : DiscordCore.getInstance().getBot().getGuilds()) {
                        try {
                            ServerSettings serverSettings = ServerSettings.getSettings(guild);
                            embedBuilder.setColor(serverSettings.getColor());
                            TextChannel textChannel = serverSettings.getChannel();
                            textChannel.sendMessage(embedBuilder.build()).queue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            config.save();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
