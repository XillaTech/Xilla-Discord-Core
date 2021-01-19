package net.xilla.discordcore.core;

import net.xilla.discordcore.library.CoreObject;
import net.xilla.discordcore.core.command.CommandData;
import net.xilla.discordcore.core.command.CommandRunCheck;

public class CommandCheck implements CoreObject, CommandRunCheck {

    @Override
    public boolean check(CommandData commandData) {
        return getServerSettings().canRunCommand(commandData);
    }

}
