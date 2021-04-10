package net.xilla.discord.setting;

import lombok.Getter;
import lombok.Setter;
import net.xilla.core.library.config.Settings;
import net.xilla.core.library.manager.StoredData;

public class ProgramSettings extends Settings {

    @Setter
    @Getter
    @StoredData
    private String programName = "Discord Core";

    @Setter
    @Getter
    @StoredData
    private String discordToken = "none";

    public ProgramSettings() {
        super("program-settings.json");

        startup();


    }

}
