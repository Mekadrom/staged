package com.higgs.staged;

import java.awt.image.BufferedImage;

/**
 * @author Justin Hartmann (zadarimm@gmail.com)
 * @since 0.0.1
 * @version 0.0.1
 */
public interface Animation {

    int NO_TRANS = 0;
    int PC = 1;
    int CONSOLE = 2;
    int MOVIE = 3;

    void setFrames(final BufferedImage... frames);

    void nextFrame();

    BufferedImage getFrame();

    BufferedImage getFrameAndInc();

}
