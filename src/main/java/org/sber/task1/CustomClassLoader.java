package org.sber.task1;

import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader extends URLClassLoader {
    public CustomClassLoader(URL[] urls) {
        super(urls);
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(className);

        try{
            if (clazz == null)
                clazz = findClass(className);
        } catch (ClassNotFoundException e) {
            clazz = super.loadClass(className);
        }

        return clazz;
    }
}
