package net.xilla.discordcore;

import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.command.handler.SpigotCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordCoreSpigot extends JavaPlugin {

    private static DiscordCoreSpigot instance;

    public static DiscordCoreSpigot getInstance() {
        return instance;
    }

    public DiscordCoreSpigot() {
        instance = this;
    }

    @Override
    public void onEnable(){
        new DiscordCore(Platform.getPlatform.SPIGOT.name, getDataFolder().toString() + "/", false, "Xilla Discord Core");
        getCommand("discordcore").setExecutor(new SpigotCommand());
    }

    @Override
    public void onDisable(){
        DiscordCore.getInstance().shutdown();
    }
}
