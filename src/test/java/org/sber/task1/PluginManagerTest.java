package org.sber.task1;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PluginManagerTest {

    @Test
    void load() throws MalformedURLException {
        Plugin plugin = new PluginImpl();

        var pluginRootDirectory = Paths.get("", "target", "test-classes", plugin.getClass()
                        .getPackageName()
                        .replace('.', File.separatorChar))
                .toAbsolutePath().toString();

        PluginManager pluginManager = new PluginManager(pluginRootDirectory);

        Plugin result = pluginManager.load(plugin.getClass().getSimpleName(),
                plugin.getClass().getCanonicalName());

        assertEquals(plugin.getClass(), result.getClass());
    }
}