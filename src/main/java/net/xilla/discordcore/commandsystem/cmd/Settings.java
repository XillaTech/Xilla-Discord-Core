package net.xilla.discordcore.commandsystem.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.api.Data;
import net.xilla.discordcore.api.Log;
import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.api.config.ConfigManager;
import net.xilla.discordcore.api.staff.StaffManager;
import net.xilla.discordcore.commandsystem.CommandObject;

import java.util.ArrayList;

public class Settings extends CommandObject {

    public Settings() {
        super("Settings", new String[] {"settings"}, "Core", "Manage the core settings", "settings", 10);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        Config config = ConfigManager.getInstance().getConfig("settings.json");
        if(event != null) {
            if(!StaffManager.getInstance().isAuthorized(event.getAuthor().getId(), 10))
                return true;
        }

        ArrayList<String> response = new ArrayList<>();
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("set")) {
                if(args.length >= 3) {
                    if(config.get(args[1]) != null) {
                        config.set(args[1], new Data().parseStringArrayNoDelimiter(2, args));
                        config.save();
                        response.add("Success! You have successfully set that value to " + new Data().parseStringArrayNoDelimiter(2, args) + ".");
                    } else {
                        response.add("Error! This is not an available setting. (Please keep in mind, it is case sensitive)");
                    }
                }
                if(response.size() == 0)
                    response.add("Error! Invalid input.");
            } else if(args[0].equalsIgnoreCase("reset")) {
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("botToken")) {
                        config.toJson().remove("botToken");
                        config.loadDefault("botToken", "example");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    } else if (args[1].equalsIgnoreCase("guildID")) {
                        config.toJson().remove("guildID");
                        config.loadDefault("guildID", "example");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    } else if (args[1].equalsIgnoreCase("commandPrefix")) {
                        config.toJson().remove("commandPrefix");
                        config.loadDefault("commandPrefix", "-");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    } else if (args[1].equalsIgnoreCase("companyName")) {
                        config.toJson().remove("companyName");
                        config.loadDefault("companyName", "Company Name");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    } else if (args[1].equalsIgnoreCase("embedColor")) {
                        config.toJson().remove("embedColor");
                        config.loadDefault("embedColor", "#5a5a5a");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    }
                }
                if (response.size() == 0)
                    response.add("Error! Invalid input.");
            } else if(args[0].equalsIgnoreCase("info")) {
                if(args.length == 2) {
                    if(config.get(args[1]) != null) {
                        response.add("Success! That value is set to `" + config.get(args[1]) + "`.");
                    } else {
                        response.add("Error! This is not an available setting. (Please keep in mind, it is case sensitive)");
                    }
                }
                if(response.size() == 0)
                    response.add("Error! Invalid input.");
            } else if(args[0].equalsIgnoreCase("list")) {
                response.add("Available Settings: botToken, guildID, commandPrefix, companyName, embedColor");
            }
        }
        if(response.size() == 0) {
            response.add("settings set <setting> <value> - Set the value of a setting\n");
            response.add("settings reset <setting> - Set the value of a setting to default\n");
            response.add("settings info <setting> - Show the value of a setting\n");
            response.add("settings list - Shows available settings");
        }

        String message = new Data().parseStringListNoDelimiter(0, response);
        if (event == null) {
            if(message.contains("Error!")) {
                for(String line : response)
                    Log.sendMessage(2, line);
            } else if(message.contains("Success!") || message.contains("Available Settings")) {
                for(String line : response)
                    Log.sendMessage(0, line);
            } else { // Help Command
                Log.sendMessage(0, new Data().getLineBreak());
                Log.sendMessage(0, "Settings");
                Log.sendMessage(0, new Data().getLineBreak());
                for(String line : response)
                    Log.sendMessage(0, " > " + line.replace("\n", ""));
                Log.sendMessage(0, new Data().getLineBreak());
            }
        } else {
                EmbedBuilder myEmbed;
                if(message.contains("Error!")) {
                    myEmbed = new Data().createEmbed("Error!", message);
                } else if(message.contains("Success!") || message.contains("Available Settings")) {
                    myEmbed = new Data().createEmbed("Success!", message);
                } else { // Help Command
                    String commandPrefix = config.getString("commandPrefix");
                    for(int i = 0; i < response.size(); i++) {
                        response.set(i, commandPrefix + response.get(i));
                    }
                    myEmbed = new Data().createEmbed("Settings", new Data().parseStringListNoDelimiter(0, response));
                }
                event.getChannel().sendMessage(myEmbed.build()).queue();
        }
        return true;
    }

}
