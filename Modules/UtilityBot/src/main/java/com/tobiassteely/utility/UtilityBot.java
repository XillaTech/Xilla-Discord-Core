package com.tobiassteely.utility;

import com.tobiassteely.utility.punishment.PunishmentCommands;
import com.tobiassteely.utility.punishment.PunishmentManager;
import com.tobiassteely.utility.punishment.PunishmentSettings;
import com.tobiassteely.utility.suggestion.SuggestionCommands;
import com.tobiassteely.utility.suggestion.SuggestionHandler;
import com.tobiassteely.utility.suggestion.SuggestionManager;
import com.tobiassteely.utility.suggestion.SuggestionSettings;
import net.xilla.discordcore.api.module.JavaModule;


public class UtilityBot extends JavaModule {

    private static UtilityBot instance;

    public static UtilityBot getInstance() {
        return instance;
    }

    private SuggestionManager suggestionManager;
    private PunishmentManager punishmentManager;

    private UtilitySettings utilitySettings;
    private SuggestionSettings suggestionSettings;
    private PunishmentSettings punishmentSettings;

    public UtilityBot() {
        super("Utility", "1.0.0");
        instance = this;
    }
    @Override
    public void onEnable() {
        this.utilitySettings = new UtilitySettings();
        if(utilitySettings.isSuggestionsEnabled()) {
            this.suggestionManager = new SuggestionManager();
            this.suggestionSettings = new SuggestionSettings();
            new SuggestionCommands();
            getDiscordCore().getBot().addEventListener(new SuggestionHandler());
        }
        if(utilitySettings.isPunishmentsEnabled()) {
            this.punishmentSettings = new PunishmentSettings();
            this.punishmentManager = new PunishmentManager();
            new PunishmentCommands();
        }
        new UtilityCommands();
    }

    public SuggestionSettings getSuggestionSettings() {
        return suggestionSettings;
    }

    public SuggestionManager getSuggestionManager() {
        return suggestionManager;
    }

    public PunishmentSettings getPunishmentSettings() {
        return punishmentSettings;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public UtilitySettings getUtilitySettings() {
        return utilitySettings;
    }
}
