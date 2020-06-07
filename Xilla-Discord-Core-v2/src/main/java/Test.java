import com.tobiassteely.tobiasapi.api.TobiasObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.CommandBuilder;
import net.xilla.discordcore.command.CoreCommand;
import net.xilla.discordcore.command.CoreCommandExecutor;
import net.xilla.discordcore.command.CoreCommandResponse;
import net.xilla.discordcore.command.event.BungeeCommandEvent;
import net.xilla.discordcore.command.event.SpigotCommandEvent;
import net.xilla.discordcore.platform.Platform;

public class Test extends TobiasObject {

    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        DiscordCore core = new DiscordCore(Platform.getPlatform.STANDALONE.name, null);

        CommandBuilder commandBuilder = new CommandBuilder().setModule("Test");
        commandBuilder.setName("Test").setActivators("test", "t");
        commandBuilder.setDescription("Test command");
        commandBuilder.setCommandExecutor(getExecutor()).build();
    }

    public CoreCommandExecutor getExecutor() {
        CoreCommandExecutor executor = (name, arguments, inputType, data) -> {

            String description = "The sender's name is: ";

            if(inputType.equals(CoreCommandExecutor.discord_input)) {
                MessageReceivedEvent event = (MessageReceivedEvent)data[0];
                description = description + event.getAuthor().getAsMention();

            } else if(inputType.equals(CoreCommandExecutor.bungee_input)) {
                BungeeCommandEvent event = (BungeeCommandEvent)data[0];
                description = description + event.getSender().getName();

            } else if(inputType.equals(CoreCommandExecutor.spigot_input)) {
                SpigotCommandEvent event = (SpigotCommandEvent)data[0];
                description = description + event.getSender().getName();

            } else {
                description = description + "Console";
            }

            EmbedBuilder builder = new EmbedBuilder().setTitle("Test Command").setDescription(description);
            CoreCommandResponse response = (CoreCommandResponse)getCommandManager().getResponse();
            response.send(builder, inputType, data);
        };
        return executor;
    }

}
