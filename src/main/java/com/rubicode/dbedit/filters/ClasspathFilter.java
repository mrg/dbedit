package com.rubicode.dbedit.filters;

import java.io.File;

/**
 * @author Michael Gentry
 */
public class ClasspathFilter extends Filter
{
    public boolean accept(File directory, String filename)
    {
        if (filename.toLowerCase().endsWith(".jar"))
            return true;
        else if (filename.toLowerCase().endsWith(".zip"))
            return true;

        return false;
    }

    @Override
    public boolean accept(File file)
    {
        if (file.isDirectory())
            return true;

        if (file.getName().toLowerCase().endsWith(".jar"))
            return true;
        else if (file.getName().toLowerCase().endsWith(".zip"))
            return true;

        return false;
    }

    @Override
    public String getDescription()
    {
        return "CLASSPATH Entries";
    }
}
