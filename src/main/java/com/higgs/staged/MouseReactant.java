package com.higgs.staged;

import java.awt.event.MouseEvent;

public interface MouseReactant {

    void mouseInput(final MouseEvent e, final InputListener.EnumMouseInputType type);

}
