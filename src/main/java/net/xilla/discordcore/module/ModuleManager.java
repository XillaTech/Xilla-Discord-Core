package net.xilla.discordcore.module;

import net.xilla.core.library.config.ConfigManager;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.module.type.JavaModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleManager extends Manager<String, Module> {

    public ModuleManager() {
        super("Modules");

        try {
            File file;
            if(ConfigManager.getInstance().getBaseFolder() == null) {
                file = new File("modules/");
            } else {
                file = new File(ConfigManager.getInstance().getBaseFolder() + "modules/");
            }

            file.mkdir();
            Stream<Path> walk = Files.walk(file.toPath());
            List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
            HashMap<String, Path> paths = new HashMap<>();

            for(Path path : result) {
                if(!paths.containsKey(path.toString()) && path.toString().endsWith(".jar")) {
                    Module module = null;
                    try {
                        ModuleLoader moduleLoader = new ModuleLoader(path, "Java");
                        module = moduleLoader.getModule();
                    } catch (LoadModuleException ex) {
                        Logger.log(LogLevel.FATAL, "Module (" + path + ") failed to load...", this.getClass());
                        Logger.log(LogLevel.FATAL, ex, this.getClass());
                    }

                    if(module != null) {
                        registerModule(module);
                    }
                }
                paths.put(path.toString(), path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {

    }

    @Override
    protected void objectAdded(Module module) {

    }

    @Override
    protected void objectRemoved(Module module) {

    }

    public void registerModule(Module module) {
        put(module);
        module.onEnable();
    }

    public JavaModule getJavaModule(String name) {
        Module module = get(name);
        if(module.getType().equals("Java")) {
            return (JavaModule)module;
        }
        return null;
    }

    public JavaModule getPythonModule(String name) {
        Module module = get(name);
        if(module.getType().equals("Java")) {
            return (JavaModule)module;
        }
        return null;
    }

}
