package org.sber.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader(ClassLoader parent, String key, File dir) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            byte[] codedClasses = CustomEncoder.decode(Files.readAllBytes(
                    Path.of(dir.getPath(), name + ".class")), key);

            return defineClass(name, codedClasses, 0, codedClasses.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
