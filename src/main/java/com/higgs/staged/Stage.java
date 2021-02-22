package com.higgs.staged;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class Stage implements MouseReactant, KeyReactant {
    private final List<StagedActor> _actors = new ArrayList<>();

    private Animation _animation;

    public abstract void act();

    @Override
    public void keyInput(final KeyEvent e, final InputListener.EnumKeyInputType type) {

    }

    @Override
    public void mouseInput(final MouseEvent e, final InputListener.EnumMouseInputType type) {

    }


    private int width;
    private int height;

    public Stage(final JPanel parentPanel) {
        if (parentPanel != null) {
            this.width = parentPanel.getWidth();
            this.height = parentPanel.getHeight();
        }
    }

    public Stage(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public void addActor(final StagedActor actor, final int x, final int y) {
        if (!this._actors.contains(actor)) {
            this._actors.add(actor);
        }
        actor.setLocation(x, y);
        actor.setStage(this);
    }

    public List<StagedActor> getActors() {
        return this._actors;
    }

    public void removeActor(final StagedActor actor) {
        this._actors.remove(actor);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setAnimation(final Animation animation) {
        this._animation = animation;
    }

    public Animation getAnimation() {
        return this._animation;
    }

    public ArrayList<StagedActor> getActorsAt(final double x, final double y) {
        final ArrayList<StagedActor> result = new ArrayList<>();
        for (final StagedActor actor : this._actors) {
            if (actor.getX() == x && actor.getY() == y) {
                result.add(actor);
            }
        }
        return result;
    }

    public ArrayList<StagedActor> getActorsInRange(final int x, final int y, final double range) {
        final ArrayList<StagedActor> result = new ArrayList<>();
        for (final StagedActor actor : this._actors) {
            if (actor != null) {
                if (StageUtils.dist(x, y, actor.getX(), actor.getY()) <= range) {
                    result.add(actor);
                }
            }
        }
        return result;
    }

    public ArrayList<StagedActor> getActorsInRange(final StagedActor posA, final double range) {
        final ArrayList<StagedActor> result = new ArrayList<>();
        for (final StagedActor actor : this._actors) {
            if (actor != null) {
                if (actor != posA) {
                    if (StageUtils.dist(posA.getX(), posA.getY(), actor.getX(), actor.getY()) <= range) {
                        result.add(actor);
                    }
                }
            }
        }
        return result;
    }
}
