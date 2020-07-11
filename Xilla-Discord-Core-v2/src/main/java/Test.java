import com.tobiassteely.tobiasapi.api.TobiasObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.response.CoreCommandResponder;
import net.xilla.discordcore.command.event.BungeeCommandEvent;
import net.xilla.discordcore.command.event.SpigotCommandEvent;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.platform.Platform;

public class Test extends TobiasObject {

    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        DiscordCore core = new DiscordCore(Platform.getPlatform.STANDALONE.name, null, true, "Xilla Discord Core");

        CommandBuilder commandBuilder = new CommandBuilder().setModule("Test");
        commandBuilder.setName("Test").setActivators("test", "t");
        commandBuilder.setDescription("Test command");
        commandBuilder.setCommandExecutor(getExecutor()).build();
    }

    public CoreCommandExecutor getExecutor() {
        return (name, arguments, data) -> {

            String description = "The sender's name is: ";

            if(data.getInputType().equals(CoreCommandExecutor.discord_input)) {
                MessageReceivedEvent event = (MessageReceivedEvent)data.get();
                description = description + event.getAuthor().getAsMention();

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
        };
    }

}
