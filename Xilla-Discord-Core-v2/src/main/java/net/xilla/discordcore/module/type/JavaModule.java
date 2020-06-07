package net.xilla.discordcore.module.type;

import net.xilla.discordcore.module.Module;

public class JavaModule extends Module {

    public JavaModule(String name, String version) {
        super("Java", name, version);
    }

    public void onEnable() {
        getLog().sendMessage(2, "Java Module " + getName() + " (" + getVersion() + ") is not starting properly. Please contact the developer.");
        getLog().sendMessage(2, "If you are the developer, make sure to override the onEnable() in your main class.");
    }

    public void onDisable() {
        getLog().sendMessage(2, "Java Module " + getName() + " (" + getVersion() + ") is not stopping properly. Please contact the developer.");
        getLog().sendMessage(2, "If you are the developer, make sure to override the onDisable() in your main class.");
    }
}
