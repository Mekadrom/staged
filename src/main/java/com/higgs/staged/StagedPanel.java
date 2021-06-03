package com.higgs.staged;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StagedPanel extends JPanel {
    private Stage stage;

    public StagedPanel(final Stage stage, final boolean start) {
        this.stage = stage;
        this.init();

        if (start) {
            this.start();
        }
    }

    private void init() {
        this.addListeners();
    }

    private void addListeners() {
        final InputListener listener = new InputListener() {
            @Override
            public void key(final KeyEvent e, final EnumKeyInputType type) {
                if (StagedPanel.this.stage != null) {
                    StagedPanel.this.stage.keyInput(e, type);
                    for (final StagedActor actor : StagedPanel.this.stage.getActors()) {
                        if (e.isConsumed()) break;
                        actor.keyInput(e, type);
                    }
                }
            }

            @Override
            public void mouse(final MouseEvent e, final EnumMouseInputType type) {
                if (StagedPanel.this.stage != null) {
                    StagedPanel.this.stage.mouseInput(e, type);
                    for (final StagedActor actor : StagedPanel.this.stage.getActors()) {
                        if (e.isConsumed()) break;
                        actor.mouseInput(e, type);
                    }
                }
            }
        };
        this.addMouseListener(listener);
        this.addKeyListener(listener);
    }

    public void start() {
        final StagedPanel ref = this;

        final Thread logicThread = new Thread(() -> {
            final int TARGET_FPS = 60;
            final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

            while (ref.isVisible()) {
                final long start = System.nanoTime();
                final long elapsed;
                final long delta;

                this.logic();
                this.repaint();

                elapsed = System.nanoTime() - start;

                delta = OPTIMAL_TIME - elapsed;
                if (delta >= 0) {
                    try {
                        Thread.sleep(delta / 1000000);
                    } catch (final InterruptedException e) {
                        System.out.println("Error keeping fps at 60");
                        e.printStackTrace();
                    }
                } else {
                    Thread.yield();
                }
            }
        });
        logicThread.start();
    }

    private void logic() {
        if (this.stage != null) {
            this.stage.act();
            final Map<StagedActor, Integer> tempMap = new ConcurrentHashMap<>();
            for (int i = 0; i < this.stage.getActors().size(); i++) {
                tempMap.put(this.stage.getActors().get(i), i);
            }
            int index = 0;
            for (final StagedActor actor : tempMap.keySet()) {
                if (index < this.stage.getActors().size()) {
                    if (this.stage.getActors().get(index) != null) actor.act();
                } else {
                    break;
                }
                index++;
            }
        }
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        g.drawImage(this.getImage(), 0, 0, null);
    }

    private Image getImage() {
        final BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g = image.createGraphics();

        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if (this.stage.getAnimation() != null) g.drawImage(this.stage.getAnimation().getFrameAndInc(), 0, 0, null);
        if (this.stage != null) {
            if (this.stage.getAnimation() != null) {
                g.drawImage(this.stage.getAnimation().getFrameAndInc(), 0, 0, null);
            }

            final List<StagedActor> actors = this.stage.getActors();
            if (!actors.isEmpty()) {
                for (final StagedActor actor : actors) {
                    final Animation animation = actor.getAnimation();
                    if (animation != null) {
                        final int size = Math.max(animation.getWidth(), animation.getHeight()) * 2;

                        final BufferedImage preRot = animation.getFrame();
                        final BufferedImage rot = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

                        final double rads = Math.toRadians(actor.getAngle());
                        final double x = actor.getX() - (rot.getWidth() / 2.0);
                        final double y = actor.getY() - (rot.getHeight() / 2.0);

                        final Graphics2D g2d = rot.createGraphics();
                        g2d.rotate(rads, (double) rot.getWidth() / 2, (double) rot.getHeight() / 2);
                        g2d.drawImage(preRot, (rot.getWidth() / 2) - (preRot.getWidth() / 2), (rot.getHeight() / 2) - (preRot.getHeight() / 2), null);
                        g2d.dispose();

                        g.drawImage(rot, (int) Math.round(x), (int) Math.round(y), null);
                    }
                }
            }
        }
        g.dispose();
        return image;
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }
}
