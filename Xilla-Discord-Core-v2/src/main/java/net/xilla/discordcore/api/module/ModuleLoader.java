package net.xilla.discordcore.api.module;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class ModuleLoader {

    private String mainClass;
    private Module module;

    public ModuleLoader(Path file, String type) throws LoadModuleException {
        if(type.equals("Java")) {
            loadJavaModule(file);
        }
    }

    public void loadJavaModule(Path file) throws LoadModuleException {
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
                // Load The Module

                this.module = loadClass(file.toFile(), mainClass);

            } catch (IOException | ParseException ex) {
                throw new LoadModuleException("File (" + file.toString() + ") does not contain a valid plugin.json file. Please make sure all files in the module folder are valid addons!");
            }
        } else
            throw new LoadModuleException("File (" + file.toString() + ") is not a .jar file. Please make sure all files in the module folder are valid addons!");
    }

    public String getMainClass() {
        return mainClass;
    }

    public Module getModule() {
        return module;
    }

    public Module loadClass(File jar, String classpath) {
        try {
            ClassLoader loader = URLClassLoader.newInstance(
                    new URL[] { jar.toURL() },
                    getClass().getClassLoader()
            );
            Class<?> clazz = Class.forName(classpath, true, loader);
            Class<? extends Module> newClass = clazz.asSubclass(Module.class);
            Constructor<? extends Module> constructor = newClass.getConstructor();
            return constructor.newInstance();

        } catch (ClassNotFoundException | MalformedURLException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
