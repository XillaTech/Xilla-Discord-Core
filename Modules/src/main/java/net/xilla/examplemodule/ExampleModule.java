package net.xilla.examplemodule;

import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.Log;
import net.xilla.discordcore.api.config.Config;
import net.xilla.discordcore.commandsystem.CommandManager;
import net.xilla.discordcore.commandsystem.MessageEventHandler;
import net.xilla.discordcore.commandsystem.MessageEventManger;
import net.xilla.discordcore.module.Module;
import net.xilla.examplemodule.command.TestCommand;
import net.xilla.examplemodule.handler.MessageEvent;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ExampleModule extends Module {

    @Override
    public boolean start() {
        loadSettings();
        loadCommands();
        loadHandlers();
        return true;
    }

    public void loadSettings() {
        Log.sendMessage(3, "Loading Example Module Settings..."); // Sends a verbose message, only prints if verbose is enabled in the core.
        // Grabs the config
        Config config = DiscordCore.getInstance().getConfigManager().getConfig("modules/examplemodule/settings.json");

        // Basic Settings
        config.loadDefault("example-string", "this is a setting");
        config.loadDefault("example-int", 100);
        config.loadDefault("example-boolean", true);

        // A Map
        Map<String, String> map = new HashMap<>();
        map.put("object1", "data1");
        map.put("object2", "data2");
        map.put("object3", "data3");
        config.loadDefault("example-map", new JSONObject(map));

        // Saves config in case any defaults were loaded.
        config.save();

        // Now you can grab the data like below
        String string = config.getString("example-string");
        int integer = config.getInt("example-int");
        boolean bool = config.getBoolean("example-boolean");
        Map<String, String> mapLoaded = config.getMap("example-map");
    }

    public void loadCommands() {
        Log.sendMessage(3, "Loading Example Module Commands..."); // Sends a verbose message, only prints if verbose is enabled in the core.
        CommandManager commandManager = DiscordCore.getInstance().getCommandManager();
        commandManager.registerCommand(new TestCommand());
    }

    public void loadHandlers() {
        Log.sendMessage(3, "Loading Example Module Handlers..."); // Sends a verbose message, only prints if verbose is enabled in the core.
        MessageEventManger messageEventManger = DiscordCore.getInstance().getMessageEventManger();
        messageEventManger.registerMessageEvent(new MessageEvent());
    }
}
