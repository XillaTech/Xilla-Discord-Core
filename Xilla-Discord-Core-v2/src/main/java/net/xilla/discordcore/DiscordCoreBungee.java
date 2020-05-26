package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.api.Log;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.md_5.bungee.api.plugin.Plugin;
import net.xilla.discordcore.command.CommandEventHandler;
import net.xilla.discordcore.command.CommandManager;
import net.xilla.discordcore.platform.CoreSettings;
import net.xilla.discordcore.platform.Platform;
import net.xilla.discordcore.platform.cmd.BungeeCommand;
import net.xilla.discordcore.staff.StaffManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class DiscordCoreBungee extends Plugin {

    @Override
    public void onEnable(){
        new DiscordCore(Platform.getPlatform.BUNGEE.getName(), getDataFolder().toString());
        getProxy().getPluginManager().registerCommand(this, new BungeeCommand());
    }

    @Override
    public void onDisable(){
        Log.sendMessage(1, "This plugin is not able to be reloaded! If this is a reload it will continue as normal in the background until a full restart.");
    }

    private static DiscordCoreBungee instance;

    public static DiscordCoreBungee getInstance() {
        return instance;
    }
}
