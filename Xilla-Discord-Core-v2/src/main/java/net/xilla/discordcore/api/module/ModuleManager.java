package net.xilla.discordcore.api.module;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.xilla.discordcore.api.module.type.JavaModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleManager extends ManagerParent {

    public ModuleManager(String baseFolder) {
        super(false);

        try {
            Path file;
            if(baseFolder == null) {
                file = Paths.get("modules/");
            } else {
                file = Paths.get(baseFolder + "/modules/");
            }

            file.toFile().mkdir();
            Stream<Path> walk = Files.walk(file);
            List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
            HashMap<String, Path> paths = new HashMap<>();

            for(Path path : result) {
                if(!paths.containsKey(path.toString()) && path.toString().endsWith(".jar")) {
                    Module module = null;
                    try {
                        ModuleLoader moduleLoader = new ModuleLoader(path, "Java");
                        module = moduleLoader.getModule();
                    } catch (LoadModuleException ex) {
                        getLog().sendMessage(2, "Module (" + path + ") failed to load...");
                        getLog().sendMessage(2, ex.getMessage());
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
    public void reload() {
        getLog().sendMessage(2, "As of now you cannot reload the module manager. Sorry!");
    }

    public void registerModule(Module module) {
        addObject(module);
        module.onEnable();
    }

    public JavaModule getJavaModule(String name) {
        Module module = (Module)getObjectWithKey(name);
        if(module.getType().equals("Java")) {
            return (JavaModule)module;
        }
        return null;
    }

    public JavaModule getPythonModule(String name) {
        Module module = (Module)getObjectWithKey(name);
        if(module.getType().equals("Java")) {
            return (JavaModule)module;
        }
        return null;
    }

}
