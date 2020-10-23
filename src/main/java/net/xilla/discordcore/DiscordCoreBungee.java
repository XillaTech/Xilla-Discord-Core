package net.xilla.discordcore;

import net.md_5.bungee.api.plugin.Plugin;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.command.handler.BungeeCommand;

public class DiscordCoreBungee extends Plugin {

    private static DiscordCoreBungee instance;

    public static DiscordCoreBungee getInstance() {
        return instance;
    }

    public DiscordCoreBungee() {
        instance = this;
    }

    @Override
    public void onEnable(){
        new DiscordCore(Platform.getPlatform.BUNGEE.name, getDataFolder().toString(), false, "Xilla Discord Core");
        getProxy().getPluginManager().registerCommand(this, new BungeeCommand());
    }

    @Override
    public void onDisable(){
        DiscordCore.getInstance().shutdown();
    }
}
