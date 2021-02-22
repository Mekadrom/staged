package com.higgs.staged;

import java.awt.image.BufferedImage;

public class ComplexAnimation extends SimpleAnimation {
    private int[] smoothnesses;
    private final int[] smoothnessCounters;

    public ComplexAnimation(final int[] smoothnesses, final BufferedImage[] frames) {
        if (frames.length != smoothnesses.length) {
            throw new IllegalArgumentException("Arrays must have the same length!");
        }

        this.setFrames(frames);
        this.setSmoothnesses(smoothnesses);
        this.smoothnessCounters = new int[smoothnesses.length];

        int i = 0;
        while (i < this.smoothnessCounters.length) {
            this.smoothnessCounters[i++] = 0;
        }
    }

    public void setSmoothnesses(final int[] smoothnesses) {
        this.smoothnesses = smoothnesses;
    }

    @Override
    public void nextFrame() {
        if (this.frames != null) {
            this.smoothnessCounters[this.frame]++;
            if (this.smoothnesses[this.frame] != Animation.NO_TRANS) {
                if (this.smoothnessCounters[this.frame] >= this.smoothnesses[this.frame]) {
                    this.incFrame(1);
                    this.smoothnessCounters[this.frame] = 0;
                }
            } else {
                this.incFrame(1);
            }
        }
    }
}
