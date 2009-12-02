package com.rubicode.dbedit.windows;

import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;

import org.apache.log4j.Logger;

import com.rubicode.dbedit.Application;
import com.rubicode.dbedit.Constants;

/**
 * @author Michael Gentry
 */
public class MainWindow extends WindowController
{
    private static final int DEFAULT_X = 100;
    private static final int DEFAULT_Y = 100;

    private static final int MINIMUM_HEIGHT = 600;
    private static final int MINIMUM_WIDTH  = 640;

    private static final Logger log = Logger.getLogger(WindowController.class);

    //    private JButton    button;
    private JTree tree;

    public MainWindow() throws Exception
    {
        super("MainWindow.xml", "MainMenu.xml");

        tree.setModel(null);
        tree.setRootVisible(false);

//        System.out.println("tree: " + getTree());
    }

    @Override
    public void initialize()
    {
        Preferences preferences = Application.getPreferences();
        JFrame      window      = getWindow();

        // Set or restore previous location.
        window.setLocation(preferences.getInt(Constants.MAIN_WINDOW_X, DEFAULT_X),
                           preferences.getInt(Constants.MAIN_WINDOW_Y, DEFAULT_Y));

        // Set or restore previous size.
        window.setSize(preferences.getInt(Constants.MAIN_WINDOW_WIDTH, MINIMUM_WIDTH),
                       preferences.getInt(Constants.MAIN_WINDOW_HEIGHT, MINIMUM_HEIGHT));
    }

    public void modelChanged()
    {
        log.debug("model changed");
    }

    @Override
    public void windowResized(ComponentEvent event)
    {
        try
        {
            Preferences preferences = Application.getPreferences();
            JFrame      window      = getWindow();

            log.debug("window resized");

            if (window.getHeight() < MINIMUM_HEIGHT)
                window.setSize(window.getWidth(), MINIMUM_HEIGHT);
            if (window.getWidth() < MINIMUM_WIDTH)
                window.setSize(MINIMUM_WIDTH, window.getHeight());

            preferences.putInt(Constants.MAIN_WINDOW_WIDTH, window.getWidth());
            preferences.putInt(Constants.MAIN_WINDOW_HEIGHT, window.getHeight());

            preferences.flush();
        }
        catch (BackingStoreException e)
        {
            log.error("Error updating preference settings.", e);
        }
    }

    /**
     * Invoked when the window's position changes.
     */
    @Override
    public void windowMoved(ComponentEvent event)
    {
        try
        {
            Preferences preferences = Application.getPreferences();
            JFrame      window      = getWindow();

            log.debug("window moved");

            preferences.putInt(Constants.MAIN_WINDOW_X, window.getX());
            preferences.putInt(Constants.MAIN_WINDOW_Y, window.getY());

            preferences.flush();
        }
        catch (Exception e)
        {
            log.error("Error updating preference settings.", e);
        }
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        log.debug("Window closing");

        if (Application.isSafeToQuit())
        {
            int option = JOptionPane.showConfirmDialog(getWindow(),
                                                       "Are you sure you want to quit?",
                                                       "Quit DBEdit",
                                                       JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION)
                System.exit(0);
            else if (option == JOptionPane.NO_OPTION)
                log.debug("NO");
        }
    }

    public void setTree(JTree tree)
    {
        this.tree = tree;
    }

    public JTree getTree()
    {
        return tree;
    }
}
