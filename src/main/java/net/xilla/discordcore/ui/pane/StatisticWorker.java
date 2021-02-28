package net.xilla.discordcore.ui.pane;

import net.xilla.core.library.manager.XillaManager;
import net.xilla.core.library.worker.Worker;
import net.xilla.discordcore.library.DiscordAPI;
import net.xilla.discordcore.ui.MenuManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StatisticWorker extends Worker {

    public StatisticWorker() {
        super("StatisticsPane", 100);
    }

    @Override
    public void runWorker(long l) {
        if(MenuManager.getInstance().getCurrentPane().equals("Statistics")) {

            List<String> gNames = new ArrayList<>();
            List<String> mNames = new ArrayList<>();

            DecimalFormat df = new DecimalFormat("###,###,###");

            DiscordAPI.getBot().getGuilds().forEach(guild -> gNames.add(guild.getName() + " (" + df.format(guild.getMemberCount()) + ")"));
            XillaManager.getInstance().iterate().forEach(manager -> mNames.add(manager.getName() + " (" + df.format(manager.size()) + ")"));

            MenuManager.getInstance().getStatisticPane().getStatistics().setText(
                    "Guilds (" + gNames.size() + "): " + String.join(", ", gNames) + "\n\n" +
                    "Managers (" + mNames.size() + "): " + String.join(", ", mNames)
            );
        }
    }

}
