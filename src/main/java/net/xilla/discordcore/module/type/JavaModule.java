package net.xilla.discordcore.module.type;

import net.xilla.discordcore.module.Module;

public abstract class JavaModule extends Module {

    public JavaModule(String name, String version) {
        super("Java", name, version);
    }

    public abstract void onEnable();

    public abstract void onDisable();

}
