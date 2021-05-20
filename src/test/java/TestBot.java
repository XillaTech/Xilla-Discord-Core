import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.xilla.core.library.Pair;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.setting.ProgramSettings;
import org.junit.Test;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class TestBot {

    public static void main(String[] args) {
        Pair<DiscordCore, ProgramSettings> data = DiscordCore.createBot();

        DiscordCore.startBot(data.getValueOne(), data.getValueTwo());

        new TestBot();
    }

    public TestBot() {
        TestManager testManager = new TestManager();

        testManager.load();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Title A");
        builder.setDescription("Example Description");

        System.out.println("Post " + testManager.iterate());
        testManager.save();
    }


}
