package com.higgs.staged;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Optional;

public abstract class StagedActor implements MouseReactant, KeyReactant {
    private double x;
    private double y;
    private double theta;

    private Animation animation;
    private Stage stage;

    
    public abstract void act();


    @Override
    public void keyInput(final KeyEvent e, final InputListener.EnumKeyInputType type) {

    }

    @Override
    public void mouseInput(final MouseEvent e, final InputListener.EnumMouseInputType type) {

    }


    public void setAnimation(final Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public int getWidth() {
        return Optional.ofNullable(this.animation).map(Animation::getFrame).map(BufferedImage::getWidth).orElse(-1);
    }

    public int getHeight() {
        return Optional.ofNullable(this.animation).map(Animation::getFrame).map(BufferedImage::getHeight).orElse(-1);
    }

    public void setX(final int x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    public void setAngle(final double theta) {
        this.theta = theta;
    }

    public double getAngle() {
        return this.theta;
    }

    public void setLocation(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D getLocation() {
        return new Vector2D(this.x, this.y);
    }

    public int getRadius() {
        int radius = 0;
        if (this.animation != null) {
            final BufferedImage frame = this.animation.getFrame();
            final int width = frame.getWidth();
            final int height = frame.getHeight();
            radius = (int) (((double) width + (double) height) / 4.0);
        }
        return radius;
    }
}
