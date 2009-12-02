package com.rubicode.dbedit.utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Largely copied from Cayenne's FileClassLoadingService.
 *
 * @author Michael Gentry
 */
public class ClassLoaderUtility
{
    private final List<File> paths = new ArrayList<File>();

    private FileClassLoader classLoader;

    public ClassLoader getClassLoader()
    {
        if (classLoader == null)
            classLoader = new FileClassLoader(getClass().getClassLoader(), paths);

        return classLoader;
    }

    /**
     * Returns class for a given name, loading it if needed from configured locations.
     */
    public synchronized Class<?> loadClass(String className) throws ClassNotFoundException
    {
        return getClassLoader().loadClass(className);
    }

    /**
     * Adds files (JAR/ZIP/Directory) to the class path.
     *
     * @param files The list of files to add.
     */
    public synchronized void setPathFiles(Collection<File> files)
    {
        classLoader = null;

        paths.clear();

        for (File file : files)
            addFile(file);
    }

    /**
     * Adds a new location to the list of configured locations.
     */
    public synchronized void addFile(File file)
    {
        file = file.getAbsoluteFile();

        if (paths.contains(file))
            return;

        if (classLoader != null)
        {
            try
            {
                classLoader.addURL(file.toURI().toURL());
            }
            catch (MalformedURLException ex)
            {
                return;
            }
        }

        paths.add(file);
    }

    /**
     * Returns an unmodifiable list of configured CLASSPATH locations.
     */
    public synchronized List<File> getPaths()
    {
        return Collections.unmodifiableList(paths);
    }

    /**
     * URLClassLoader with addURL method exposed.
     */
    static class FileClassLoader extends URLClassLoader
    {
        FileClassLoader(ClassLoader parent)
        {
            super(new URL[0], parent);
        }

        FileClassLoader(ClassLoader parent, List<File> files)
        {
            this(parent);

            for (File file : files)
            {
                try
                {
                    addURL(file.toURI().toURL());
                }
                catch (MalformedURLException ex)
                {
                    // FIXME: Log this?
                }
            }
        }

        @Override
        public void addURL(URL url)
        {
            super.addURL(url);
        }
    }
}
