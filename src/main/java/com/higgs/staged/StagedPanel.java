package com.higgs.staged;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Justin Hartmann (zadarimm@gmail.com)
 * @since 0.0.1
 * @version 0.0.1
 */
public class StagedPanel extends JPanel {
    private Stage _stage;

    public StagedPanel(final Stage stage, boolean start) {
        Objects.requireNonNull(_stage = stage);
        init();

        if(start) start();
    }

    private void init() {
        addListeners();
    }

    private void addListeners() {
        final InputListener listener = new InputListener() {
            public void key(final KeyEvent e, final EnumKeyInputType type) {
                if(_stage != null) {
                    _stage.keyInput(e, type);
                    for(final StagedActor actor : _stage.getActors()) {
                        if(e.isConsumed()) break;
                        actor.keyInput(e, type);
                    }
                }
            }

            public void mouse(final MouseEvent e, final EnumMouseInputType type) {
                if(_stage != null) {
                    _stage.mouseInput(e, type);
                    for(final StagedActor actor : _stage.getActors()) {
                        if(e.isConsumed()) break;
                        actor.mouseInput(e, type);
                    }
                }
            }
        };
        addMouseListener(listener);
        addKeyListener(listener);
    }

    public void start() {
        final StagedPanel ref = this;

        final Thread logicThread = new Thread(() -> {
            final int TARGET_FPS = 60;
            final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

            while(ref.isVisible()) {
                long start = System.nanoTime(), elapsed, delta;

                logic();
                repaint();

                elapsed = System.nanoTime() - start;

                delta = OPTIMAL_TIME - elapsed;
                if(delta >= 0) {
                    try {
                        Thread.sleep(delta / 1000000);
                    } catch(final InterruptedException e) {
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
        if(_stage != null) {
            _stage.act();
            final Map<StagedActor, Integer> tempMap = new ConcurrentHashMap<>();
            for(int i = 0; i < _stage.getActors().size(); i++) {
                tempMap.put(_stage.getActors().get(i), i);
            }
            int index = 0;
            for(final StagedActor actor : tempMap.keySet()) {
                if(index < _stage.getActors().size()) {
                    if(_stage.getActors().get(index) != null) actor.act();
                } else {
                    break;
                }
                index++;
            }
        }
    }

    public void paint(final Graphics g) {
        super.paint(g);
        g.drawImage(getImage(), 0, 0, null);
    }

    private Image getImage() {
        final BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g = image.createGraphics();

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if(_stage.getAnimation() != null) g.drawImage(_stage.getAnimation().getFrameAndInc(), 0, 0, null);
        if(_stage != null) {
            if(_stage.getAnimation() != null) {
                g.drawImage(_stage.getAnimation().getFrameAndInc(), 0, 0, null);
            }

            final List<StagedActor> actors = _stage.getActors();
            if(!actors.isEmpty()) {
                for(final StagedActor actor : actors) {
                    final Animation animation = actor.getAnimation();
                    if(animation != null) {
//                        final BufferedImage rotated = new BufferedImage(actor.getWidth() * 2, actor.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
//                        Graphics2D g2d = rotated.createGraphics();
//                        g2d.rotate(actor.getAngle(), actor.getWidth() / 2.0, actor.getHeight() / 2.0);
//                        g2d.drawImage(actor.getAnimation().getFrameAndInc(), 0, 0, null);
//                        g.drawImage(rotated, actor.getX() - (actor.getWidth() / 2), actor.getY() - (actor.getHeight() / 2), null);

//                        final double rads = Math.toRadians(actor.getAngle());
//                        final double sin = Math.abs(Math.sin(rads));
//                        final double cos = Math.abs(Math.cos(rads));
//                        final int w = (int) Math.floor((actor.getWidth() * cos) + (actor.getHeight() * sin));
//                        final int h = (int) Math.floor((actor.getHeight() * cos) + (actor.getWidth() * sin));
//
//                        final BufferedImage rotatedImage = new BufferedImage(w * 2, h * 2, animation.getFrame().getType());
//                        final AffineTransform at = new AffineTransform();
//
//                        at.translate(w / 2.0, h / 2.0);
//                        at.rotate(rads,0, 0);
//                        at.translate(-actor.getWidth() / 2.0, -actor.getHeight() / 2.0);
//
//                        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//                        rotateOp.filter(animation.getFrame(), rotatedImage);
//                        g.drawImage(rotatedImage, actor.getX() - (actor.getWidth() / 2), actor.getY() - (actor.getHeight() / 2), null);
//                        animation.nextFrame();

                        double rads = Math.toRadians(actor.getAngle());
                        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
                        int w = actor.getWidth();
                        int h = actor.getHeight();
                        int newWidth = (int) Math.floor(w * cos + h * sin);
                        int newHeight = (int) Math.floor(h * cos + w * sin);

                        final BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = rotated.createGraphics();
                        AffineTransform at = new AffineTransform();
                        at.translate((newWidth - w) / 2.0, (newHeight - h) / 2.0);

                        at.rotate(rads, actor.getWidth() / 2.0, actor.getHeight() / 2.0);
                        g.setTransform(at);
                        g.drawImage(animation.getFrameAndInc(), 0, 0, this);
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);

//                        final AffineTransform tx = new AffineTransform();
//
//                        tx.translate(actor.getWidth() / 2.0,actor.getHeight() / 2.0);
//                        tx.rotate(Math.toRadians(actor.getAngle()));
//                        tx.translate(-actor.getWidth() / 2.0,-actor.getHeight() / 2.0);
//
//                        final AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//
//                        final BufferedImage rotated =new BufferedImage(actor.getWidth(), actor.getHeight(), animation.getFrame().getType());
//                        op.filter(animation.getFrame(), rotated);
//
//                        g.drawImage(rotated, actor.getX() - (actor.getWidth() / 2), actor.getY() - (actor.getHeight() / 2), null);

//                        final BufferedImage imgNew = new BufferedImage(actor.getWidth() * 2, actor.getHeight() * 2, animation.getFrame().getType());
//                        final Graphics2D g2d = (Graphics2D)imgNew.getGraphics();
//                        g2d.rotate(Math.toRadians(actor.getAngle()), imgNew.getWidth() / 2.0, imgNew.getHeight() / 2.0);
//                        g2d.drawImage(animation.getFrame(), 0, 0, null);
//                        g.drawImage(imgNew, actor.getX() - (actor.getWidth() / 2), actor.getY() - (actor.getHeight() / 2), null);
//                        animation.nextFrame();
                    }
                }
            }
        }
        return image;
    }

    public void setStage(final Stage stage) {
        _stage = stage;
    }
}
