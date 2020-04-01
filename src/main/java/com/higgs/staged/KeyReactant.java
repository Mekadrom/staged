package com.higgs.staged;

import java.awt.event.KeyEvent;

/**
 * @author Justin Hartmann (zadarimm@gmail.com)
 * @since 0.0.1
 * @version 0.0.1
 */
public interface KeyReactant {
    void keyInput(final KeyEvent e, final InputListener.EnumKeyInputType type);
}
