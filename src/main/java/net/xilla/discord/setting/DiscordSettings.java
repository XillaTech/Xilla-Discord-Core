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

    public DiscordSettings() {
        super("discord-settings.json");
    }

}
