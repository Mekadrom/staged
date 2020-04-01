package com.higgs.staged;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author Justin Hartmann (zadarimm@gmail.com)
 * @since 0.0.1
 * @version 0.0.1
 */
public class ComplexAnimation extends SimpleAnimation {
    private int[] _smoothnesses;
    private int[] _smoothnessCounters;

    public ComplexAnimation(final int[] smoothnesses, final BufferedImage[] frames) {
        if(frames.length != smoothnesses.length) {
            throw new IllegalArgumentException("Arrays must have the same length!");
        }

        setFrames(frames);
        setSmoothnesses(smoothnesses);
        _smoothnessCounters = new int[smoothnesses.length];

        int i = 0;
        while(i < _smoothnessCounters.length) _smoothnessCounters[i++] = 0;
    }

    public void setSmoothnesses(final int[] smoothnesses) {
        Objects.requireNonNull(smoothnesses);
        _smoothnesses = smoothnesses;
    }

    @Override
    public void nextFrame() {
        if(_frames != null) {
            _smoothnessCounters[_frame]++;
            if(_smoothnesses[_frame] != NO_TRANS) {
                if(_smoothnessCounters[_frame] >= _smoothnesses[_frame]) {
                    incFrame(1);
                    _smoothnessCounters[_frame] = 0;
                }
            } else {
                incFrame(1);
            }
        }
    }
}
