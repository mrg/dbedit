package com.rubicode.dbedit.windows;

import javax.swing.JTextArea;

/**
 * @author Michael Gentry
 */
public class ExceptionWindow extends WindowController
{
    private static ExceptionWindow instance = null;

    private JTextArea textArea;

    public static ExceptionWindow getInstance() throws Exception
    {
        return new ExceptionWindow();
//        if (instance == null)
//            instance = new ExceptionWindow();
//
//        return instance;
    }

    public void setException(Exception e)
    {
        textArea.setText(e.getMessage());
    }

    public void setEditorPane(JTextArea textArea)
    {
        this.textArea = textArea;
    }

    private ExceptionWindow() throws Exception
    {
        super("ExceptionWindow.xml", "MainMenu.xml");
    }
}
