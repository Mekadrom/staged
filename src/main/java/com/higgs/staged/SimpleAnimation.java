package com.higgs.staged;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Optional;

public class SimpleAnimation implements Animation {
    private int smoothness = 1;
    private int smoothCount = 0;

    int frame = 0;

    protected BufferedImage[] frames;

    public SimpleAnimation() { }

    public SimpleAnimation(final int smoothness, final BufferedImage... frames) {
        this.setSmoothness(smoothness);
        this.setFrames(frames);
    }

    public void setSmoothness(final int smoothness) {
        this.smoothness = smoothness;
    }

    public int getSmoothness() {
        return this.smoothness;
    }

    @Override
    public void setFrames(final BufferedImage... frames) {
        Objects.requireNonNull(frames);
        this.frames = frames;
    }

    @Override
    public void nextFrame() {
        if (this.frames != null) {
            this.smoothCount++;
            if (this.smoothness != Animation.NO_TRANS) {
                if (this.smoothCount >= this.smoothness) {
                    this.incFrame(1);
                    this.smoothCount = 0;
                }
            } else {
                this.incFrame(1);
            }
        }
    }

    protected void incFrame(final int amount) {
        if (this.frame + amount >= this.frames.length) {
            this.frame = 0;
        } else {
            this.frame += amount;
        }
    }

    @Override
    public BufferedImage getFrame() {
        return this.frames[this.frame];
    }

    @Override
    public BufferedImage getFrameAndInc() {
        this.nextFrame();
        return this.getFrame();
    }

    @Override
    public int getWidth() {
        return Optional.ofNullable(this.getFrame()).map(BufferedImage::getWidth).orElse(-1);
    }

    @Override
    public int getHeight() {
        return Optional.ofNullable(this.getFrame()).map(BufferedImage::getHeight).orElse(-1);
    }
}
