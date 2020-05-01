package net.xilla.examplemodule.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.discordcore.api.Data;
import net.xilla.discordcore.commandsystem.CommandObject;
import net.xilla.discordcore.commandsystem.CommandResponse;

import java.util.Arrays;
import java.util.Collections;

public class TestCommand extends CommandObject {

    public TestCommand() {
        // String name, String[] activators, String module, String description, String usage, int staffLevel
        super("TestCommand", new String[] {"testcommand"}, "ExampleModule", "A test command that embeds what you say!", "testcommand <message>", 5);

        // Staff Level of 0: Anyone can use it
        // Staff Level of 5: Mods can use it
        // Staff Level of 10: Admins can use it
        // You can also configure your own staff ranks within the core.
    }

    @Override
    public boolean run(String[] args, MessageReceivedEvent event) {
        CommandResponse commandResponse; // Initializes the command response
        if(event == null) {// Console command
            // Creates the command response required
            commandResponse = new CommandResponse("Console Event", Arrays.asList("Silly console, this command's for discord!", "Good try though!"));
        } else {// Discord command
            if(args.length > 0) {
                // If they have a message to embed

                // String Builder turns their message from args back into a text string.
                StringBuilder message = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    if (i != args.length - 1) {
                        message.append(args[i]).append(" "); // Adds it with a space
                    } else {
                        message.append(args[i]); // Adds it without a space, as it's the last word.
                    }
                }

                // Creates the command response required
                commandResponse = new CommandResponse("Announcement", Collections.singletonList(message.toString()));
            } else {
                // If they do not have a message to embed

                // Creates the command response required
                commandResponse = new CommandResponse("Hey!", Collections.singletonList("Please specify a message to embed."));
            }

            // Sends the command response to the appropriate person.
            commandResponse.sendResponse(event);
        }
        return true; // Just telling the core we successfully processed the command.
    }
}
