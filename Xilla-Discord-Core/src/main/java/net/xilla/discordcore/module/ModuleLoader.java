package net.xilla.discordcore.module;

import net.xilla.discordcore.api.ExtensionLoader;
import net.xilla.discordcore.module.exception.LoadModuleException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Path;

public class ModuleLoader {

    private String mainClass;
    private String version;
    private String name;
    private Module module;

    public ModuleLoader(Path file) throws LoadModuleException {
        String inputFile = "jar:file:" + file.toString() + "!/module.json";
        if (inputFile.startsWith("jar:")){
            try {
                URL inputURL = new URL(inputFile);
                JarURLConnection conn = (JarURLConnection)inputURL.openConnection();
                InputStream in = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                StringBuilder fileData = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    fileData.append(line + System.lineSeparator());
                }


                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject)parser.parse(String.valueOf(fileData));
                this.mainClass = (String)json.get("mainClass");
                this.version = (String)json.get("version");
                this.name = (String)json.get("name");

                // Load The Module
                ExtensionLoader<Module> loader = new ExtensionLoader<>();
                try {
                    this.module = loader.LoadClass(file.toFile(), mainClass, Module.class);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (IOException | ParseException e1) {
                throw new LoadModuleException("File (" + file.toString() + ") does not contain a valid plugin.json file. Please make sure all files in the module folder are valid addons!");
            }
        } else
            throw new LoadModuleException("File (" + file.toString() + ") is not a .jar file. Please make sure all files in the module folder are valid addons!");
    }

    public Module getModule() {
        return module;
    }

    public String getName() {
        return name;
    }

    public String getMainClass() {
        return mainClass;
    }

    public String getVersion() {
        return version;
    }
}
