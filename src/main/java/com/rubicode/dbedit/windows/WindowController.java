package com.rubicode.dbedit.windows;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.cayenne.conf.Configuration;
import org.apache.cayenne.conf.FileConfiguration;
import org.apache.log4j.Logger;
import org.swixml.SwingEngine;

import com.rubicode.dbedit.Application;

/**
 * @author Michael Gentry
 */
public abstract class WindowController extends WindowEvents
{
    private static final String interfaceDirectory = "xml/";
    private static final Logger log = Logger.getLogger(WindowController.class);

    private final SwingEngine swingEngine;
    private final JFrame      window;

    private JMenu    editMenu;
    private JMenu    fileMenu;
    private JMenu    helpMenu;
    private JMenuBar menubar;
    private boolean  modified;
    private JMenu    windowMenu;

    protected WindowController(String interfaceFilename, String menuFilename) throws Exception
    {
        // Create and load the window.
        swingEngine = new SwingEngine(this);
        window      = (JFrame) getSwingEngine().render(interfaceDirectory + interfaceFilename);

        // Load the menu for the window.
        if (SwingEngine.isMacOSX())
        {
            log.info("Loading OS X Menu");
            menubar = (JMenuBar) getSwingEngine().render(interfaceDirectory + "OSX" + menuFilename);
        }
        else
        {
            log.info("Loading non-OS X Menu");
            menubar = (JMenuBar) getSwingEngine().render(interfaceDirectory + menuFilename);
        }

        // Finish configuring window settings.
        window.addComponentListener(this);
        window.addWindowListener(this);
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        window.setJMenuBar(menubar);

        // Allow subclasses to initialize further values.
        initialize();

        // Make the window visible.
        window.setVisible(true);

//        minimumHeight = (int) window.getSize().getHeight();
//        minimumWidth  = (int) window.getSize().getWidth();

//        JPanel panel = (JPanel) window.getContentPane();
//
//        panel.revalidate();
//        panel.setMinimumSize(panel.getSize());
//        window.pack();
//        window.setMinimumSize(window.getSize());

        // Handle resizes below minimum size.  (Yes, this is awful.)
//        window.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e)
//            {
//                if (window.getHeight() < minimumHeight)
//                    window.setSize(window.getWidth(), minimumHeight);
//                if (window.getWidth() < minimumWidth)
//                    window.setSize(minimumWidth, window.getHeight());
//            }
//        });

//        System.out.println("Minimum size: " + window.getContentPane().getMinimumSize());

        // Notify the Application of a new window to monitor.
        Application.addWindowController(this);
    }

    public void initialize()
    {
        // Subclass responsibility.
    }

    /////////////////////////////////////////////////////////////////////////
    // Events
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void windowClosing(WindowEvent event)
    {
        log.debug("Window closing");

        if (isModified())
        {
            int option = JOptionPane.showConfirmDialog(getWindow(), "Save changes before closing?");

            if (option == JOptionPane.YES_OPTION)
                log.debug("YES");
            else if (option == JOptionPane.NO_OPTION)
                log.debug("NO");
            else if (option == JOptionPane.CANCEL_OPTION)
                log.debug("CANCEL");
            else if (option == JOptionPane.NO_OPTION)
                window.dispose();
        }
        else
        {
            Application.removeWindowController(this);
            window.dispose();
        }
    }

    @Override
    public void windowClosed(WindowEvent event)
    {
        log.debug("Window closed");
//        window.dispose();
    }

    @Override
    public void windowActivated(WindowEvent event)
    {
        log.debug("Window activated");
//        System.out.println("windowMenu = " + windowMenu);
//        System.out.println("fileMenu = " + fileMenu);
//        System.out.println(e);

        Application.setActiveWindowController(this);
    }

    @Override
    public void windowStateChanged(WindowEvent event)
    {
        log.debug("Window state changed");
        log.debug(event);
    }

    @Override
    public void windowGainedFocus(WindowEvent event)
    {
        log.debug("Window gained focus");
        log.debug(event);
    }

    /////////////////////////////////////////////////////////////////////////
    // Actions
    /////////////////////////////////////////////////////////////////////////

    /**
     * Open a model.  Displays the file chooser for opening a model file.
     *
     * @throws Exception
     */
    public void open() throws Exception
    {
        log.debug("Open called");
        File selectedFile = Dialogs.openModel();

        log.debug(selectedFile);

        if (selectedFile != null)
        {
            String filename = selectedFile.getAbsolutePath();
            File file = new File(filename);

            try
            {
                FileConfiguration conf = new FileConfiguration(file);
                Configuration.initializeSharedConfiguration(conf);
            }
            catch (Exception e)
            {
                ExceptionWindow ew = ExceptionWindow.getInstance();
                ew.setException(e);
                ew.getWindow().setVisible(true);
                log.debug(e);
            }
        }
    }

    /**
     * Display the About window.
     *
     * @throws Exception
     */
    public void about() throws Exception
    {
        AboutWindow aboutWindow = AboutWindow.getInstance();

//        URL url = new URL("file:text/AboutWindow.html");
        aboutWindow.getWindow().setVisible(true);
        log.debug("About called");
    }

    /**
     * Display the Preferences window.
     *
     * @throws Exception
     */
    public void preferences() throws Exception
    {
        PreferencesWindow preferencesWindow = PreferencesWindow.getInstance();

        preferencesWindow.getWindow().setVisible(true);

        log.debug("preferences called");
    }

    /**
     * Close the current window.
     */
    public void close()
    {
        windowClosing(null);
    }

    public void copy()
    {
        Component component = getWindow().getFocusOwner();

        if (component instanceof JTextArea)
            ((JTextArea) component).copy();
        else if (component instanceof JTextField)
            ((JTextField) component).copy();
        else
            getWindow().getToolkit().beep();
    }

    public void cut()
    {
        Component component = getWindow().getFocusOwner();

        if (component instanceof JTextArea)
            ((JTextArea) component).cut();
        else if (component instanceof JTextField)
            ((JTextField) component).cut();
        else
            getWindow().getToolkit().beep();
    }

    public void paste()
    {
        Component component = getWindow().getFocusOwner();

        if (component instanceof JTextArea)
            ((JTextArea) component).paste();
        else if (component instanceof JTextField)
            ((JTextField) component).paste();
        else
            getWindow().getToolkit().beep();
    }

    public void quit()
    {
        log.debug("Quit");
        if (Application.isSafeToQuit())
            System.exit(0);
    }

    /////////////////////////////////////////////////////////////////////////
    // Accessors
    /////////////////////////////////////////////////////////////////////////

    public void setModified(boolean modified)
    {
        this.modified = modified;
    }

    public boolean isModified()
    {
        return modified;
    }

    public SwingEngine getSwingEngine()
    {
        return swingEngine;
    }

    public String getTitle()
    {
        return getWindow().getTitle();
    }

    public JFrame getWindow()
    {
        return window;
    }

    public JMenu getFileMenu()
    {
        return this.fileMenu;
    }

    public void setFileMenu(JMenu fileMenu)
    {
        this.fileMenu = fileMenu;
    }

    public JMenu getEditMenu()
    {
        return this.editMenu;
    }

    public void setEditMenu(JMenu editMenu)
    {
        this.editMenu = editMenu;
    }

    public JMenu getWindowMenu()
    {
        return this.windowMenu;
    }

    public void setWindowMenu(JMenu windowMenu)
    {
        this.windowMenu = windowMenu;
    }

    public JMenu getHelpMenu()
    {
        return this.helpMenu;
    }

    public void setHelpMenu(JMenu helpMenu)
    {
        this.helpMenu = helpMenu;
    }
}
