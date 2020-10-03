package net.xilla.discordcore.command;

import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.core.command.CommandRunCheck;

public class CommandCheck extends CoreObject implements CommandRunCheck {

    @Override
    public boolean check(CommandData commandData) {
        return getCommandSettings().isCommand(commandData);
    }

}
