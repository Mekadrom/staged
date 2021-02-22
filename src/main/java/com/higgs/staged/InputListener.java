package com.higgs.staged;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class InputListener implements MouseListener, KeyListener {
    public enum EnumMouseInputType {
        CLICKED, PRESSED, RELEASED, ENTERED, EXITED
    }

    public enum EnumKeyInputType {
        TYPED, PRESSED, RELEASED
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        this.key(e, EnumKeyInputType.TYPED);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        this.key(e, EnumKeyInputType.PRESSED);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        this.key(e, EnumKeyInputType.RELEASED);
    }

    public abstract void key(KeyEvent e, final EnumKeyInputType type);

    @Override
    public void mouseClicked(final MouseEvent e) {
        this.mouse(e, EnumMouseInputType.CLICKED);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        this.mouse(e, EnumMouseInputType.PRESSED);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        this.mouse(e, EnumMouseInputType.RELEASED);
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        this.mouse(e, EnumMouseInputType.ENTERED);
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        this.mouse(e, EnumMouseInputType.EXITED);
    }

    public abstract void mouse(MouseEvent e, final EnumMouseInputType type);
}
