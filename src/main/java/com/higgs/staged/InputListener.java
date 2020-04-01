package com.higgs.staged;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Wrapper class for input that stuffs all mouse input into one method
 * with an enum determining the type of input. Inefficient at runtime
 * (kind of), but sleeker in code. I can make some sacrifices if I want
 * to, this is my damn project!
 *
 * @author Justin Hartmann (zadarimm@gmail.com)
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class InputListener implements MouseListener, KeyListener {
    public enum EnumMouseInputType {
        CLICKED, PRESSED, RELEASED, ENTERED, EXITED
    }

    public enum EnumKeyInputType {
        TYPED, PRESSED, RELEASED
    }

    @Override
    public void keyTyped(KeyEvent e) {
        key(e, EnumKeyInputType.TYPED);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        key(e, EnumKeyInputType.PRESSED);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        key(e, EnumKeyInputType.RELEASED);
    }

    public abstract void key(KeyEvent e, final EnumKeyInputType type);

    @Override
    public void mouseClicked(final MouseEvent e) {
        mouse(e, EnumMouseInputType.CLICKED);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        mouse(e, EnumMouseInputType.PRESSED);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        mouse(e, EnumMouseInputType.RELEASED);
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        mouse(e, EnumMouseInputType.ENTERED);
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        mouse(e, EnumMouseInputType.EXITED);
    }

    public abstract void mouse(MouseEvent e, final EnumMouseInputType type);
}
