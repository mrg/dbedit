package com.rubicode.dbedit.filters;

import java.io.File;

/**
 * @author Michael Gentry
 */
public class CayenneModelFilter extends Filter
{
    public boolean accept(File directory, String filename)
    {
        if (filename.equals("cayenne.xml"))
            return true;

        return false;
    }

    @Override
    public boolean accept(File file)
    {
        if (file.isDirectory())
            return true;

        if (file.getName().equals("cayenne.xml"))
            return true;

        return false;
    }

    @Override
    public String getDescription()
    {
        return "cayenne.xml";
    }
}
