package net.xilla.discordcore.module.type;

import com.tobiassteely.tobiasapi.api.Log;
import net.xilla.discordcore.module.Module;

public class PythonModule extends Module {

    public PythonModule(String name, String version) {
        super("Python", name, version);
    }

    public void onEnable() {
        Log.sendMessage(2, "Python Module " + getName() + " (" + getVersion() + ") is not starting properly. Please contact the developer.");
        Log.sendMessage(2, "If you are the developer, make sure to override the onEnable() in your main class.");
    }

    public void onDisable() {
        Log.sendMessage(2, "Python Module " + getName() + " (" + getVersion() + ") is not stopping properly. Please contact the developer.");
        Log.sendMessage(2, "If you are the developer, make sure to override the onDisable() in your main class.");
    }
}
