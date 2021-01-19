package net.xilla.discordcore.library.program;

import net.xilla.discordcore.library.CoreObject;

public interface ProgramInterface extends CoreObject {

    default DiscordProgram getProgram() {
        return DiscordProgram.getProgram();
    }

    default DiscordController getController() {
        return DiscordProgram.getProgram().getDiscordController();
    }

}
