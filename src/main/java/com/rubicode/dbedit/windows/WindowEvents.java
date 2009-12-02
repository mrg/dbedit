package com.rubicode.dbedit.windows;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

/**
 * This entire class exists to provide nasty little stub handlers for Java.
 * Subclasses are expected to override these methods if they need to handle
 * the event.
 *
 * @author Michael Gentry
 */
public abstract class WindowEvents implements ComponentListener, WindowListener, WindowStateListener, WindowFocusListener
{
    /**
     * Invoked when the window's size changes.
     */
    public void componentResized(ComponentEvent event) { windowResized(event); }
    public void windowResized(ComponentEvent event) {}
    /**
     * Invoked when the window's position changes.
     */
    public void componentMoved(ComponentEvent event) { windowMoved(event); }
    public void windowMoved(ComponentEvent event) {}

    /**
     * Invoked when the window has been made visible.
     */
    public void componentShown(ComponentEvent event) {}

    /**
     * Invoked when the window has been made invisible.
     */
    public void componentHidden(ComponentEvent event) {}

    /**
     * Invoked when a window has been opened.
     */
    public void windowOpened(WindowEvent event) {}

    /**
     * Invoked when a window is in the process of being closed.
     * The close operation can be overridden at this point.
     */
    public void windowClosing(WindowEvent event) {}

    /**
     * Invoked when a window has been closed.
     */
    public void windowClosed(WindowEvent event) {}

    /**
     * Invoked when a window is iconified.
     */
    public void windowIconified(WindowEvent event) {}

    /**
     * Invoked when a window is de-iconified.
     */
    public void windowDeiconified(WindowEvent event) {}

    /**
     * Invoked when a window is activated.
     */
    public void windowActivated(WindowEvent event) {}

    /**
     * Invoked when a window is deactivated.
     */
    public void windowDeactivated(WindowEvent event) {}

    /**
     * Invoked when a window state is changed.
     */
    public void windowStateChanged(WindowEvent event) {}

    /**
     * Invoked when the Window is set to be the focused Window, which means
     * that the Window, or one of its subcomponents, will receive keyboard
     * events.
     */
    public void windowGainedFocus(WindowEvent event) {}

    /**
     * Invoked when the Window is no longer the focused Window, which means
     * that keyboard events will no longer be delivered to the Window or any of
     * its subcomponents.
     */
    public void windowLostFocus(WindowEvent event) {}
}
