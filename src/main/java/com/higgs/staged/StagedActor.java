package com.higgs.staged;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author Justin Hartmann (zadarimm@gmail.com)
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class StagedActor implements MouseReactant, KeyReactant {
    private int _x;
    private int _y;
    private double _theta;

    private Animation _animation;
    private Stage _stage;

    public abstract void act();


    public void keyInput(final KeyEvent e, final InputListener.EnumKeyInputType type) {

    }

    public void mouseInput(final MouseEvent e, final InputListener.EnumMouseInputType type) {

    }


    public void setAnimation(final Animation animation) {
        Objects.requireNonNull(_animation = animation);
    }

    public Animation getAnimation() {
        return _animation;
    }

    public void setStage(final Stage stage) {
        _stage = stage;
    }

    public Stage getStage() {
        return _stage;
    }

    public int getWidth() {
        if(_animation == null || _animation.getFrame() == null) return 0;
        return _animation.getFrame().getWidth();
    }

    public int getHeight() {
        if(_animation == null || _animation.getFrame() == null) return 0;
        return _animation.getFrame().getHeight();
    }

    public void setX(int x) {
        _x = x;
    }

    public int getX() {
        return _x;
    }

    public void setY(int y) {
        _y = y;
    }

    public int getY() {
        return _y;
    }

    public void setAngle(double theta) {
        _theta = theta;
    }

    public double getAngle() {
        return _theta;
    }

    public void setLocation(int x, int y) {
        _x = x;
        _y = y;
    }

    public Point getLocation() {
        return new Point(_x, _y);
    }

    public int getRadius() {
        int radius = 0;
        if(_animation != null) {
            final BufferedImage frame = _animation.getFrame();
            int width = frame.getWidth();
            int height = frame.getHeight();
            radius = (int)(((double)width + (double)height) / 4.0);
        }
        return radius;
    }
}
