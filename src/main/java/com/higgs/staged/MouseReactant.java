package com.higgs.staged;

import java.awt.event.MouseEvent;

/**
 * @author Justin Hartmann (zadarimm@gmail.com)
 * @since 0.0.1
 * @version 0.0.1
 */
public interface MouseReactant {
    void mouseInput(final MouseEvent e, final InputListener.EnumMouseInputType type);
}
