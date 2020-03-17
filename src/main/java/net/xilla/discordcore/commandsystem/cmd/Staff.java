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

public class Staff extends CommandObject {

    public Staff() {
        super("Staff", new String[] {"staff"}, "Core", "Manage the staff roles", "staff", 10);
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        Config settings = ConfigManager.getInstance().getConfig("settings.json");

        if(event != null) {
            if(!StaffManager.getInstance().isAuthorized(event.getAuthor().getId(), 10))
                return true;
        }

        ArrayList<String> response = new ArrayList<>();
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("edit")) {
                if(args.length == 4) {
                    net.xilla.discordcore.api.staff.Staff staff = StaffManager.getInstance().getStaff(args[1]);
                    if(staff != null) {
                        if (args[2].equalsIgnoreCase("level")) {
                            staff.setLevel(Integer.parseInt(args[3]));
                            StaffManager.getInstance().save();
                            response.add("Success! You have edited the staff rank.");
                        } else if (args[2].equalsIgnoreCase("groupID")) {
                            staff.setGroupID(args[3]);
                            StaffManager.getInstance().save();
                            response.add("Success! You have edited the staff rank.");
                        } else if (args[2].equalsIgnoreCase("name")) {
                            staff.setName(args[3]);
                            StaffManager.getInstance().save();
                            response.add("Success! You have edited the staff rank.");
                        } else {
                            response.add("Available Staff Options: level, groupID, name");
                        }
                    } else {
                        response.add("Error! That is not a valid staff rank. Please note, the staff rank is case sensitive.");
                    }
                } else {
                    response.add("Available Staff Options: level, groupID, name");
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                StringBuilder groups = new StringBuilder();
                for(int i = 0; i < StaffManager.getInstance().getList().size(); i++) {
                    if(i == StaffManager.getInstance().getList().size() - 1) {
                        groups.append(StaffManager.getInstance().getList().get(i).getKey());
                    } else {
                        groups.append(StaffManager.getInstance().getList().get(i).getKey()).append(", ");
                    }
                }
                response.add("Available Staff Groups: " + groups);
            }
        }
        if(response.size() == 0) {
            response.add("staff edit <group> <option> <value> - Edit a staff group\n");
            response.add("staff edit <group> - View available options\n");
            response.add("staff list - View staff groups\n");
        }

        String message = new Data().parseStringListNoDelimiter(0, response);
        if (event == null) {
            if(message.contains("Error!")) {
                for(String line : response)
                    Log.sendMessage(2, line);
            } else if(message.contains("Success!") || message.contains("Available Staff")) {
                for(String line : response)
                    Log.sendMessage(0, line);
            } else { // Help Command
                Log.sendMessage(0, new Data().getLineBreak());
                Log.sendMessage(0, "Staff");
                Log.sendMessage(0, new Data().getLineBreak());
                for(String line : response)
                    Log.sendMessage(0, " > " + line.replace("\n", ""));
                Log.sendMessage(0, new Data().getLineBreak());
            }
        } else {
            EmbedBuilder myEmbed;
            if(message.contains("Error!")) {
                myEmbed = new Data().createEmbed("Error!", message);
            } else if(message.contains("Success!") || message.contains("Available Staff")) {
                myEmbed = new Data().createEmbed("Success!", message);
            } else { // Help Command
                String commandPrefix = settings.getString("commandPrefix");
                for(int i = 0; i < response.size(); i++) {
                    response.set(i, commandPrefix + response.get(i));
                }
                myEmbed = new Data().createEmbed("Staff", new Data().parseStringListNoDelimiter(0, response));
            }
            event.getChannel().sendMessage(myEmbed.build()).queue();
        }
        return true;
    }
}
