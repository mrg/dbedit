package com.rubicode.dbedit.windows;

/**
 * @author Michael Gentry
 */
public class AboutWindow extends WindowController
{
    private static AboutWindow instance = null;

    public static AboutWindow getInstance() throws Exception
    {
        if (instance == null)
            instance = new AboutWindow();

        return instance;
    }

    private AboutWindow() throws Exception
    {
        super("AboutWindow.xml", "MainMenu.xml");
    }
}
