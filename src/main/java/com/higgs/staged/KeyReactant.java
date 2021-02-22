package com.higgs.staged;

import java.awt.event.KeyEvent;

public interface KeyReactant {

    void keyInput(final KeyEvent e, final InputListener.EnumKeyInputType type);

}
