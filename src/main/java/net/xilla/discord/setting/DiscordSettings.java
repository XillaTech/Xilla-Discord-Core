package net.xilla.discord.setting;

import lombok.Getter;
import net.xilla.core.library.config.Settings;
import net.xilla.core.library.manager.StoredData;

import java.util.ArrayList;
import java.util.List;

public class DiscordSettings extends Settings {

    @Getter
    @StoredData
    private List<String> admins = new ArrayList<>();

    @Getter
    @StoredData
    private String prefix = "-";

    @Getter
    @StoredData
    private String color = "#e207ff";

    @Getter
    @StoredData
    private Boolean respectOwner = true;

    @Getter
    @StoredData
    private Boolean respectAdmin = true;

    public DiscordSettings() {
        super("discord-settings.json");
    }

}
