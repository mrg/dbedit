package com.rubicode.dbedit;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.rubicode.dbedit.utilities.ClassLoaderUtility;
import com.rubicode.dbedit.windows.MainWindow;
import com.rubicode.dbedit.windows.WindowController;

/**
 * @author Michael Gentry
 */
public class Application
{
    // Check that we are on Mac OS X. This is crucial to loading and using the OSXAdapter class.
//    public static final boolean MAC_OS_X = (System.getProperty("os.name").toLowerCase().startsWith("mac os x"));

    private static WindowController            activeWindowController = null;
    private static ClassLoaderUtility          applicationClassLoader = null;
    private static MainWindow                  mainWindow             = null;
    private static Preferences                 preferences            = Preferences.userNodeForPackage(Application.class);
    private static ArrayList<WindowController> windowControllers      = new ArrayList<WindowController>();

    public static void addWindowController(WindowController windowController)
    {
        windowControllers.add(windowController);

        if (windowController instanceof MainWindow)
            mainWindow = (MainWindow) windowController;
    }

    public static void removeWindowController(WindowController windowController)
    {
        windowControllers.remove(windowController);

        if (windowController instanceof MainWindow)
            mainWindow = null;
    }

    public static void setActiveWindowController(WindowController windowController)
    {
        activeWindowController = windowController;

        refreshWindowMenu();
    }

    public static void refreshWindowMenu()
    {
        activeWindowController.getWindowMenu().removeAll();

        for (WindowController windowController : windowControllers)
        {
            Action callback = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("You clicked a window" + e);
                    System.out.println("Your window controller = " + getValue("windowController"));
                    WindowController windowController = (WindowController) getValue("windowController");
                    windowController.getWindow().toFront();
                }
            };

            callback.putValue("windowController", windowController);

            JMenuItem menuItem = new JMenuItem(callback);
            menuItem.setText(windowController.getWindow().getTitle());
            activeWindowController.getWindowMenu().add(menuItem);
        }
    }

    /**
     * Checks all registered windows for modifications.  If all are unmodified,
     * it is safe to quit the application.  If any have changes, then it is not.
     *
     * @return <tt>true</tt> if no registered windows contain modifications,
     *         <tt>false</tt> otherwise.
     */
    public static boolean isSafeToQuit()
    {
        for (WindowController windowController : windowControllers)
            if (windowController.isModified())
                return false;

        return true;
    }

    /**
     * Initializes the class path based upon the preferences settings.
     */
    public static void initializeClassPath()
    {
        ClassLoaderUtility classLoader = new ClassLoaderUtility();
        ArrayList<File>    files       = new ArrayList<File>();

        for (int i = 0; i < 100; i++)
        {
            String path = getPreferences().get("classpath_" + i, null);

            if (path == null)
                break; // No more!

            files.add(new File(path));
        }

        classLoader.setPathFiles(files);

        applicationClassLoader = classLoader;

        if (SwingUtilities.isEventDispatchThread())
        {
            Thread.currentThread().setContextClassLoader(applicationClassLoader.getClassLoader());
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable() {
                public void run()
                {
                    Thread.currentThread().setContextClassLoader(applicationClassLoader.getClassLoader());
                }
            });
        }
    }

    public static MainWindow getMainWindow()
    {
        return mainWindow;
    }

    public static Preferences getPreferences()
    {
        return preferences;
    }
}
