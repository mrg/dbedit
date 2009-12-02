package com.rubicode.dbedit.windows;

import java.io.File;
import java.util.prefs.BackingStoreException;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.rubicode.dbedit.Application;

/**
 * @author Michael Gentry
 */
public class PreferencesWindow extends WindowController
{
    private static PreferencesWindow instance = null;

    private JList            jarList;
    private DefaultListModel list;

    public static PreferencesWindow getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new PreferencesWindow();

        }

        System.out.println("list = " + instance.jarList);
        return instance;
    }

    private PreferencesWindow() throws Exception
    {
        super("PreferencesWindow.xml", "MainMenu.xml");
    }

    @Override
    public void initialize()
    {
        list = new DefaultListModel();

        for (int i = 0; i < 100; i++)
        {
            String path = Application.getPreferences().get("classpath_" + i, null);
System.out.println("path = " + path);
            if (path == null)
                break; // No more!

            list.addElement(path);
        }

        jarList.setModel(list);
    }

    private final int counter = 1;

    public void addJAR()
    {
        File file = Dialogs.openJar();
        System.out.println(file);
        Application.initializeClassPath();
        System.out.println("Adding JAR");
        list.addElement(file.toString());

        rewriteClasspathPreferences();
    }

    public void removeJAR()
    {
        System.out.println("Removing JAR " + jarList.getSelectedIndex());
        if (jarList.getSelectedIndex() >= 0)
        {
            list.remove(jarList.getSelectedIndex());
            Application.initializeClassPath();
            rewriteClasspathPreferences();
        }
    }

    private void rewriteClasspathPreferences()
    {
        System.out.println("rewriting classpath preferences");
        try
        {
            // First, remove all the keys.
            for (String key : Application.getPreferences().keys())
                if (key.startsWith("classpath_"))
                    Application.getPreferences().remove(key);

            // Then fill them all in again.
            for (int i = 0; i < list.size(); i++)
                Application.getPreferences().put("classpath_" + i, (String) list.get(i));
        }
        catch (BackingStoreException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
