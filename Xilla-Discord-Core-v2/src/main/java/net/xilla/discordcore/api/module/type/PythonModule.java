package net.xilla.discordcore.api.module.type;

import net.xilla.discordcore.api.module.Module;

public class PythonModule extends Module {

    public PythonModule(String name, String version) {
        super("Python", name, version);
    }

    public void onEnable() {
        getLog().sendMessage(2, "Python Module " + getName() + " (" + getVersion() + ") is not starting properly. Please contact the developer.");
        getLog().sendMessage(2, "If you are the developer, make sure to override the onEnable() in your main class.");
    }

    public void onDisable() {
        getLog().sendMessage(2, "Python Module " + getName() + " (" + getVersion() + ") is not stopping properly. Please contact the developer.");
        getLog().sendMessage(2, "If you are the developer, make sure to override the onDisable() in your main class.");
    }
}
