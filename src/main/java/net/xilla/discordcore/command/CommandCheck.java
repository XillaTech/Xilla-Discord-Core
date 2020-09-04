package net.xilla.discordcore.command;

import com.tobiassteely.tobiasapi.command.CommandData;
import com.tobiassteely.tobiasapi.command.CommandRunCheck;
import net.xilla.discordcore.CoreObject;

public class CommandCheck extends CoreObject implements CommandRunCheck {

    @Override
    public boolean check(CommandData commandData) {
        return getCommandSettings().isCommand(commandData);
    }

}
