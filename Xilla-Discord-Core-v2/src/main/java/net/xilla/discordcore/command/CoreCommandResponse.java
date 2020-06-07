package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.CommandResponse;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.xilla.discordcore.command.event.BungeeCommandEvent;
import net.xilla.discordcore.command.event.SpigotCommandEvent;
import org.bukkit.ChatColor;


public class CoreCommandResponse extends TobiasObject implements CommandResponse {

    // Overriding the internal send command with title
    @Override
    public void send(String title, String text, String inputType, Object... data) {
        // Sends a discord response if it's a discord command
        if(inputType.equals(CoreCommandExecutor.discord_input)) {
            MessageReceivedEvent event = (MessageReceivedEvent) data[0]; // Pulls the event from the command data
            event.getTextChannel().sendMessage(title + "\n" + text).queue(); // Sends the discord command response

        // Sends a response back to spigot
        } else if(inputType.equals(CoreCommandExecutor.bungee_input)) {
            BungeeCommandEvent event = (BungeeCommandEvent) data[0]; // Pulls the event from the command data
            event.getSender().sendMessage(new ComponentBuilder(title).color(net.md_5.bungee.api.ChatColor.RED).create());
            event.getSender().sendMessage(new ComponentBuilder(text).color(net.md_5.bungee.api.ChatColor.RED).create());

        // Sends a response back to bungee
        } else if(inputType.equals(CoreCommandExecutor.spigot_input)) {
            SpigotCommandEvent event = (SpigotCommandEvent) data[0]; // Pulls the event from the command data
            event.getSender().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + title);
            event.getSender().sendMessage(ChatColor.RED.toString() + text);

        // Sends a response back to command line
        } else {
            getLog().sendMessage(0, title, text);
        }
    }

    // Overriding the internal send command
    @Override
    public void send(String text, String inputType, Object... data) {
        // Sends a discord response if it's a discord command
        if(inputType.equals(CoreCommandExecutor.discord_input)) {
            MessageReceivedEvent event = (MessageReceivedEvent) data[0]; // Pulls the event from the command data
            event.getTextChannel().sendMessage(text).queue(); // Sends the discord command response

            // Sends a response back to spigot
        } else if(inputType.equals(CoreCommandExecutor.bungee_input)) {
            BungeeCommandEvent event = (BungeeCommandEvent) data[0]; // Pulls the event from the command data
            event.getSender().sendMessage(new ComponentBuilder(text).color(net.md_5.bungee.api.ChatColor.RED).create());

            // Sends a response back to bungee
        } else if(inputType.equals(CoreCommandExecutor.spigot_input)) {
            SpigotCommandEvent event = (SpigotCommandEvent) data[0]; // Pulls the event from the command data
            event.getSender().sendMessage(ChatColor.RED.toString() + text);

            // Sends a response back to command line
        } else {
            getLog().sendMessage(0, text);
        }
    }

    // Implementing a new Embed supported option for discord core commands.
    public void send(EmbedBuilder embedBuilder, String inputType, Object... data) {
        MessageEmbed embed = embedBuilder.build(); // Builds the MessageEmbed

        // Sends a discord response if it's a discord command
        if(inputType.equals(CoreCommandExecutor.discord_input)) {
            MessageReceivedEvent event = (MessageReceivedEvent)data[0]; // Pulls the event from the command data
            event.getTextChannel().sendMessage(embed).queue();

        // Sends a response back to spigot
        } else if(inputType.equals(CoreCommandExecutor.bungee_input)) {
            BungeeCommandEvent event = (BungeeCommandEvent) data[0]; // Pulls the event from the command data
            if(embed.getTitle() != null) {
                event.getSender().sendMessage(new ComponentBuilder(embed.getTitle()).color(net.md_5.bungee.api.ChatColor.RED).create());
            }
            event.getSender().sendMessage(new ComponentBuilder(embed.getDescription()).color(net.md_5.bungee.api.ChatColor.RED).create());

        // Sends a response back to bungee
        } else if(inputType.equals(CoreCommandExecutor.spigot_input)) {
            SpigotCommandEvent event = (SpigotCommandEvent) data[0]; // Pulls the event from the command data
            if(embed.getTitle() != null) {
                event.getSender().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + embed.getTitle());
            }
            event.getSender().sendMessage(ChatColor.RED.toString() + embed.getDescription());

        // Sends a response back to command line
        } else {
            if(embed.getTitle() != null) {
                getLog().sendMessage(0, embed.getTitle(), embed.getDescription());
            }
            getLog().sendMessage(0, embed.getDescription());
        }
    }

}
