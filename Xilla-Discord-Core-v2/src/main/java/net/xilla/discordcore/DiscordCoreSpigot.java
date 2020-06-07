package net.xilla.discordcore;

import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.platform.cmd.SpigotCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordCoreSpigot extends JavaPlugin {

    @Override
    public void onEnable(){
        new DiscordCore(Platform.getPlatform.SPIGOT.name, getDataFolder().toString());
        getCommand("discordcore").setExecutor(new SpigotCommand());
    }

    @Override
    public void onDisable(){
        DiscordCore.getInstance().getLog().sendMessage(1, "This plugin is not able to be reloaded! If this is a reload it will continue as normal in the background until a full restart.");
    }

    private static DiscordCoreSpigot instance;

    public static DiscordCoreSpigot getInstance() {
        return instance;
    }
}
