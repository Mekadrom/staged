package com.higgs.staged;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author Justin Hartmann (zadarimm@gmail.com)
 * @since 0.0.1
 * @version 0.0.1
 */
public class SimpleAnimation implements Animation {
    private int _smoothness = 1;
    private int _smoothCount = 0;

    int _frame = 0;

    protected BufferedImage[] _frames;

    public SimpleAnimation() { }

    public SimpleAnimation(final int smoothness, final BufferedImage... frames) {
        setSmoothness(smoothness);
        setFrames(frames);
    }

    public void setSmoothness(final int smoothness) {
        _smoothness = smoothness;
    }

    public int getSmoothness() {
        return _smoothness;
    }

    public void setFrames(final BufferedImage... frames) {
        Objects.requireNonNull(frames);
        _frames = frames;
    }

    public void nextFrame() {
        if(_frames != null) {
            _smoothCount++;
            if(_smoothness != NO_TRANS) {
                if(_smoothCount >= _smoothness) {
                    incFrame(1);
                    _smoothCount = 0;
                }
            } else {
                incFrame(1);
            }
        }
    }

    protected void incFrame(final int amount) {
        if(_frame + amount >= _frames.length) {
            _frame = 0;
        } else {
            _frame += amount;
        }
    }

    public BufferedImage getFrame() {
        return _frames[_frame];
    }

    public BufferedImage getFrameAndInc() {
        nextFrame();
        return getFrame();
    }
}
