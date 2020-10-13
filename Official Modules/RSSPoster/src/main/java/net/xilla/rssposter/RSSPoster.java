package net.xilla.rssposter;

import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.module.JavaModule;
import net.xilla.rssposter.command.RemoveAdmin;
import net.xilla.rssposter.command.SetAdmin;
import net.xilla.rssposter.command.SetChannel;
import net.xilla.rssposter.command.SetColor;
import net.xilla.rssposter.post.Feed;
import net.xilla.rssposter.post.FeedMessage;
import net.xilla.rssposter.post.RSSFeedParser;
import org.json.simple.JSONObject;

public class RSSPoster extends JavaModule {

    private static RSSPoster instance;

    public static RSSPoster getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        // Loads the discord library and handlers
        new DiscordCore(Platform.getPlatform.EMBEDDED.name, null, false, "RSS Poster");

        // Starts the bot
        RSSPoster rss = new RSSPoster();
        rss.onEnable();
        rss.getCommandManager().getCommandWorker().start();
    }

    private RSSSettings rssSettings;

    public RSSPoster() {
        super("RSSPoster", "1.0.0");

        instance = this;

        Logger.setLogLevel(LogLevel.DEBUG);

        this.rssSettings = new RSSSettings();

        getDiscordCore().addExecutor(() -> {
            for(Object object : rssSettings.getFeeds()) {
                JSONObject json = (JSONObject)object;

                RSSWorker rssWorker = new RSSWorker(json.get("name").toString(), json.get("link").toString(), 1000 * rssSettings.getRefreshTime());
                rssWorker.start();
            }
        });

        new RemoveAdmin();
        new SetAdmin();
        new SetChannel();
        new SetColor();
    }

    public RSSSettings getRssSettings() {
        return rssSettings;
    }
}
