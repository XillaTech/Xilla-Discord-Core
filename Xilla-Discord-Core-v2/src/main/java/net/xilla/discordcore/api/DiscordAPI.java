package net.xilla.discordcore.api;

import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.api.form.form.FormManager;

public class DiscordAPI extends CoreObject {

    private static DiscordAPI instance;

    public static DiscordAPI getInstance() {
        return instance;
    }

    private FormManager formManager;

    public DiscordAPI() {
        instance = this;

        this.formManager = new FormManager();
    }

    public FormManager getFormManager() {
        return formManager;
    }
}
