package net.xilla.discordcore.module;

import net.xilla.discordcore.api.Log;
import net.xilla.discordcore.module.exception.LoadModuleException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleManager {

    private HashMap<String, ModuleLoader> modules;

    public void registerModule(ModuleLoader moduleLoader) {
        modules.put(moduleLoader.getName(), moduleLoader);
    }

    public ModuleManager() {
        reload();
    }

    private void registerModule(Path path){
        try {
        if (!path.toFile().isDirectory()) {
                ModuleLoader moduleLoader = new ModuleLoader(path);
                if(moduleLoader.getModule() != null) {
                    Log.sendMessage(0, "Loading module " + moduleLoader.getName() + " (" + moduleLoader.getVersion() + ") - " + moduleLoader.getMainClass());
                    try {
                        if (moduleLoader.getModule().start()) {
                            registerModule(moduleLoader);
                        } else {
                            throw new LoadModuleException("File (" + path.toString() + ") is not a valid module. Please make sure to override the start method in your module.");
                        }
                    } catch (NoSuchMethodError ex) {
                        throw new LoadModuleException("File (" + path.toString() + ") is not a valid module. Please check all events and commands for errors.");
                    }
                } else {
                    throw new LoadModuleException("File (" + path.toString() + ") is not a valid module. Please make sure to override the start method in your module.");
                }
            }
        } catch (Exception e) {
            Log.sendMessage(2, "Failed to load module " + path);
            e.printStackTrace();
        }
    }

    public void reload() {
        modules = new HashMap<>();

        try {
            Stream<Path> walk = Files.walk(Paths.get("modules/"));
            List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
            HashMap<String, Path> paths = new HashMap<>();

            for(Path path : result) {
                if(!paths.containsKey(path.toString()) && path.toString().endsWith(".jar")) {
                    registerModule(path);
                }
                paths.put(path.toString(), path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Collection<ModuleLoader> getModules() {
        return modules.values();
    }
}
