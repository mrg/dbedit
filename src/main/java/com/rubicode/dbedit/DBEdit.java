package com.rubicode.dbedit;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.swixml.SwingEngine;

import com.rubicode.dbedit.windows.MainWindow;

/**
 * @author Michael Gentry
 */
public class DBEdit
{
    private static final Logger log = Logger.getLogger(DBEdit.class);

    public static void main(String[] args)
    {
        log.debug("Working directory: " + new File(".").getAbsolutePath());

        try
        {
            Preferences preferences = Preferences.userNodeForPackage(Application.class);

            for (String key : preferences.keys())
            {
                if ("true".equals(System.getProperty("reset", "false")))
                {
                    log.debug("Removing preference key: " + key);
                    preferences.remove(key);
                }
                else
                {
                    log.debug("Found preference key: " + key);
                }
            }
        }
        catch (BackingStoreException e)
        {
            log.error("Error resetting preferences -- ignored.", e);
        }

        Application.initializeClassPath();

        try
        {
            if (SwingEngine.isMacOSX())
            {
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DBEdit");
                UIManager.setLookAndFeel("apple.laf.AquaLookAndFeel");
            }
            else
            {
                UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
            }
        }
        catch (Exception e)
        {
            // Likely PlasticXP is not in the class path; ignore.
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                try
                {
                    new MainWindow();
                }
                catch (Exception e)
                {
                    log.error("Failed to start DBEdit", e);
                }
            }
        });
    }
}
