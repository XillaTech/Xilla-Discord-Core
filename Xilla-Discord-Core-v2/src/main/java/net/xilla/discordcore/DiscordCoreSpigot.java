package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.api.Log;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.xilla.discordcore.command.CommandEventHandler;
import net.xilla.discordcore.command.CommandManager;
import net.xilla.discordcore.platform.CoreSettings;
import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.platform.cmd.SpigotCommand;
import net.xilla.discordcore.staff.StaffManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class DiscordCoreSpigot extends JavaPlugin {

    @Override
    public void onEnable(){
        new DiscordCore(Platform.getPlatform.SPIGOT.name, getDataFolder().toString());
        getCommand("discordcore").setExecutor(new SpigotCommand());
    }

    @Override
    public void onDisable(){
        Log.sendMessage(1, "This plugin is not able to be reloaded! If this is a reload it will continue as normal in the background until a full restart.");
    }

    private static DiscordCoreSpigot instance;

    public static DiscordCoreSpigot getInstance() {
        return instance;
    }
}
