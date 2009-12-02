package com.rubicode.dbedit.windows;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFileChooser;

import org.swixml.SwingEngine;

import com.rubicode.dbedit.Application;
import com.rubicode.dbedit.filters.CayenneModelFilter;
import com.rubicode.dbedit.filters.ClasspathFilter;
import com.rubicode.dbedit.filters.Filter;

/**
 * @author Michael Gentry
 */
public class Dialogs
{
    private enum Mode { OPEN, SAVE };

    public static File openModel()
    {
        System.out.println("openModel");

        return openDialog("Open Model", Mode.OPEN, new CayenneModelFilter());
    }

    public static File openJar()
    {
        System.out.println("openJar");
        return openDialog("Select JAR/ZIP to add to CLASSPATH", Mode.OPEN, new ClasspathFilter());
    }

    private static File openDialog(String title, Mode mode, Filter filter)
    {
        File selectedFile = null;

        // Mac OS X uses AWT for the native-look open-dialog, Windows uses Swing.
        if (SwingEngine.isMacOSX())
        {
            FileDialog dialog;

            if (mode == Mode.OPEN)
                dialog = new FileDialog(Application.getMainWindow().getWindow(), title, FileDialog.LOAD);
            else
                dialog = new FileDialog(Application.getMainWindow().getWindow(), title, FileDialog.SAVE);

            dialog.setFilenameFilter(filter);
            dialog.setVisible(true);

            if (dialog.getFile() != null)
                selectedFile = new File(dialog.getDirectory() + dialog.getFile());

            dialog.dispose();
        }
        else
        {
            JFileChooser dialog = new JFileChooser();

            dialog.setFileFilter(filter);

            if (mode == Mode.OPEN)
                dialog.showOpenDialog(Application.getMainWindow().getWindow());
            else
                dialog.showSaveDialog(Application.getMainWindow().getWindow());

            selectedFile = dialog.getSelectedFile();

//            dialog.dispose();
        }

        System.out.println("Selected file: " + selectedFile);

        return selectedFile;
    }
}
