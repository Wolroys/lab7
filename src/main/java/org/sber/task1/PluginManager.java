package org.sber.task1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class PluginManager {

    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) throws MalformedURLException {
        String path = pluginRootDirectory + "/" + pluginName;

        URL[] urls = new URL[]{new File(path).toURI().toURL()};

        return loadClass(pluginClassName, urls);
    }

    private Plugin loadClass(String pluginClassName, URL[] urls){
        try(CustomClassLoader classLoader = new CustomClassLoader(urls)){
            return (Plugin) classLoader.loadClass(pluginClassName)
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                 ClassNotFoundException | InvocationTargetException e) {
            System.out.println("Something went wrong with - " + pluginClassName + ": " + e.getMessage());
        }

        return null;
    }
}
