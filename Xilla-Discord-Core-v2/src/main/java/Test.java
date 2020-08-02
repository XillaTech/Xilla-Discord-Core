import com.tobiassteely.tobiasapi.api.TobiasObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.ImageBuilder;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.response.CoreCommandResponder;
import net.xilla.discordcore.command.event.BungeeCommandEvent;
import net.xilla.discordcore.command.event.SpigotCommandEvent;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.platform.Platform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Test extends TobiasObject {

    private Map<Long, Boolean> primes;

    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        DiscordCore core = new DiscordCore(Platform.getPlatform.STANDALONE.name, null, true, "Xilla Discord Core");

        CommandBuilder commandBuilder = new CommandBuilder("Test", "Test", false);
        commandBuilder.setActivators("test", "t");
        commandBuilder.setDescription("Test command");
        commandBuilder.setCommandExecutor((data) -> {

            String description = "The sender's name is: ";

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {
                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                description = description + event.getAuthor().getAsMention();

                try {
                    getPrimes1(200);
                    ArrayList<Long> results = new ArrayList<>(primes.keySet());
                    Collections.sort(results);
                    System.out.println(results);
                    new ImageBuilder().build(event.getTextChannel(), results);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return new CoreCommandResponse(data).setDescription("Error! `" + ex.getMessage() + "`");
                }

                return null;
            } else if(data.getInputType().equals(CoreCommandExecutor.bungee_input)) {
                BungeeCommandEvent event = (BungeeCommandEvent)data.get();
                description = description + event.getSender().getName();

            } else if(data.getInputType().equals(CoreCommandExecutor.spigot_input)) {
                SpigotCommandEvent event = (SpigotCommandEvent)data.get();
                description = description + event.getSender().getName();

            } else {
                description = description + "Console";
            }

            EmbedBuilder builder = new EmbedBuilder().setTitle("Test Command").setDescription(description);
            return new CoreCommandResponse(data).setEmbed(builder.build());
        }).build();
    }


    public void getPrimes1(long max) {
        primes = new HashMap<>();
        primes.put(2L, true);
        primes.put(3L, true);

        long lastNumber = 3;
        for(long x = 2; x <= Math.sqrt(max) + 2; x++) {
            for(long y = x; y <= max / x; y += 2) {
                long value = x * y;

                if(value > lastNumber) {

                    for (long i = lastNumber + 1; i < value; i += 2) {
                        if(i % 2 != 0) {
                            primes.put(i, true);
                        }
                    }
                    lastNumber = value;
                }

                primes.remove(value);
            }
        }
    }


}
