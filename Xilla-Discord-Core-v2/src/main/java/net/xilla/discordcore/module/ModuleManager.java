package net.xilla.discordcore.module;

import com.tobiassteely.tobiasapi.api.Log;
import com.tobiassteely.tobiasapi.api.config.Config;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.module.type.JavaModule;
import net.xilla.discordcore.staff.group.Group;
import org.json.simple.JSONObject;

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
                file = Paths.get(baseFolder + "modules/");
            }

            file.toFile().mkdir();
            Stream<Path> walk = Files.walk(file);
            List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
            HashMap<String, Path> paths = new HashMap<>();

            for(Path path : result) {
                if(!paths.containsKey(path.toString()) && path.toString().endsWith(".jar")) {
                    JavaModule javaModule = null;
                    try {
                        ModuleLoader moduleLoader = new ModuleLoader(path, "Java");
                        javaModule = (JavaModule)moduleLoader.getModule();
                    } catch (LoadModuleException ex) {
                        Log.sendMessage(2, "Module (" + path + ") failed to load...");
                        Log.sendMessage(2, ex.getMessage());
                    }

                    if(javaModule != null) {
                        registerModule(javaModule);
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
        Log.sendMessage(2, "As of now you cannot reload the module manager. Sorry!");
    }

    public void registerModule(Module module) {
        addObject(module);
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
