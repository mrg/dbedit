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
 * the event/notification.
 *
 * @author Michael Gentry
 */
public abstract class WindowEvents implements ComponentListener, WindowListener, WindowStateListener, WindowFocusListener
{
    /**
     * Invoked when the window's size changes.
     */
    public void componentResized(ComponentEvent event) { windowResized(event); }
    public void windowResized(ComponentEvent event) { windowResized(); }
    public void windowResized() {}

    /**
     * Invoked when the window's position changes.
     */
    public void componentMoved(ComponentEvent event) { windowMoved(event); }
    public void windowMoved(ComponentEvent event) { windowMoved(); }
    public void windowMoved() {}

    /**
     * Invoked when the window has been made visible.
     */
    public void componentShown(ComponentEvent event) { windowShown(event); }
    public void windowShown(ComponentEvent event) { windowShown(); }
    public void windowShown() { }

    /**
     * Invoked when the window has been made invisible.
     */
    public void componentHidden(ComponentEvent event) { windowHidden(event); }
    public void windowHidden(ComponentEvent event) { windowHidden(); }
    public void windowHidden() {}

    /**
     * Invoked when a window has been opened.
     */
    public void windowOpened(WindowEvent event) { windowOpened(); }
    public void windowOpened() {}

    /**
     * Invoked when a window is in the process of being closed.
     * The close operation can be overridden at this point.
     */
    public void windowClosing(WindowEvent event) { windowClosing(); }
    public void windowClosing() {}

    /**
     * Invoked when a window has been closed.
     */
    public void windowClosed(WindowEvent event) { windowClosed(); }
    public void windowClosed() {}

    /**
     * Invoked when a window is iconified.
     */
    public void windowIconified(WindowEvent event) { windowIconified(); }
    public void windowIconified() {}

    /**
     * Invoked when a window is de-iconified.
     */
    public void windowDeiconified(WindowEvent event) { windowDeiconified(); }
    public void windowDeiconified() {}

    /**
     * Invoked when a window is activated.
     */
    public void windowActivated(WindowEvent event) { windowActivated(); }
    public void windowActivated() {}

    /**
     * Invoked when a window is deactivated.
     */
    public void windowDeactivated(WindowEvent event) { windowDeactivated(); }
    public void windowDeactivated() {}

    /**
     * Invoked when a window state is changed.
     */
    public void windowStateChanged(WindowEvent event) { windowStateChanged(); }
    public void windowStateChanged() {}

    /**
     * Invoked when the Window is set to be the focused Window, which means
     * that the Window, or one of its subcomponents, will receive keyboard
     * events.
     */
    public void windowGainedFocus(WindowEvent event) { windowGainedFocus(); }
    public void windowGainedFocus() {}

    /**
     * Invoked when the Window is no longer the focused Window, which means
     * that keyboard events will no longer be delivered to the Window or any of
     * its subcomponents.
     */
    public void windowLostFocus(WindowEvent event) { windowLostFocus(); }
    public void windowLostFocus() {}
}
