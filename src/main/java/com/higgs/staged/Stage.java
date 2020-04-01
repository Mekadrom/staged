package com.higgs.staged;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Justin Hartmann (zadarimm@gmail.com)
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class Stage implements MouseReactant, KeyReactant {
    private final List<StagedActor> _actors = new ArrayList<>();

    protected JPanel _parent;

    private Animation _animation;

    public abstract void act();


    public void keyInput(final KeyEvent e, final InputListener.EnumKeyInputType type) {

    }

    public void mouseInput(final MouseEvent e, final InputListener.EnumMouseInputType type) {

    }


    private int _width;
    private int _height;

    public Stage(final JPanel parentPanel) {
        if(parentPanel != null) {
            _width = parentPanel.getWidth();
            _height = parentPanel.getHeight();
        }
    }

    public Stage(int width, int height) {
        _width = width;
        _height = height;
    }

    public void addActor(final StagedActor actor, int x, int y) {
        Objects.requireNonNull(actor);
        if(!_actors.contains(actor)) {
            _actors.add(actor);
        }
        actor.setLocation(x, y);
        actor.setStage(this);
    }

    public List<StagedActor> getActors() {
        return _actors;
    }

    public void removeActor(final StagedActor actor) {
        _actors.remove(actor);
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public void setAnimation(final Animation animation) {
        _animation = animation;
    }

    public Animation getAnimation() {
        return _animation;
    }

    public ArrayList<StagedActor> getActorsAt(double x, double y) {
        ArrayList<StagedActor> result = new ArrayList<>();
        for(StagedActor actor : _actors) {
            if(actor.getX() == x && actor.getY() == y) {
                result.add(actor);
            }
        }
        return result;
    }

    public ArrayList<StagedActor> getActorsInRange(int x, int y, double range) {
        ArrayList<StagedActor> result = new ArrayList<>();
        for(StagedActor actor : _actors) {
            if(actor != null) {
                if(StageUtils.dist(x, y, actor.getX(), actor.getY()) <= range) {
                    result.add(actor);
                }
            }
        }
        return result;
    }

    public ArrayList<StagedActor> getActorsInRange(StagedActor posA, double range) {
        ArrayList<StagedActor> result = new ArrayList<>();
        for(StagedActor actor : _actors) {
            if(actor != null) {
                if(actor != posA) {
                    if(StageUtils.dist(posA.getX(), posA.getY(), actor.getX(), actor.getY()) <= range) {
                        result.add(actor);
                    }
                }
            }
        }
        return result;
    }
}
