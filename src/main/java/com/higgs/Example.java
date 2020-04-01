package com.higgs;

import com.higgs.staged.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Example {
    private static final Dimension _dim = new Dimension(1366, 768);

    public static void main(final String[] args) {
        final JFrame frame = new JFrame("Example Stage");

        final JPanel contentPane = new JPanel(new BorderLayout());

        frame.setContentPane(contentPane);

        frame.setSize(_dim);
        contentPane.setSize(_dim);

        final Stage stage = new Stage(contentPane) {
            int _acts = 0;

            private void init() {
                this.setImage();

                doAdd();
            }

            private void doAdd() {
                final StagedActor actor = new StagedActor() {
                    int _acts = 0;

                    private void init() {
                        this.setImage();
                    }

                    private void setImage() {
                        final BufferedImage image = new BufferedImage(40, 100, BufferedImage.TYPE_INT_ARGB);

                        final Graphics2D g2d = image.createGraphics();
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(0, 0, 40, 100);

                        setAnimation(new SimpleAnimation(Animation.NO_TRANS, image));
                    }

                    public void act() {
                        if(this._acts == 0) init();
                        setAngle(getAngle() + 1);
                        this._acts++;
                    }
                };
                addActor(actor, 800, 300);
            }

            private void setImage() {
                final BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

                final Graphics2D g2d = image.createGraphics();
                g2d.setColor(Color.RED);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                setAnimation(new SimpleAnimation(Animation.NO_TRANS, image));
            }

            public void act() {
                if(this._acts == 0) init();
                this._acts++;
            }
        };

        final StagedPanel stagedPanel = new StagedPanel(stage, false);

        frame.getContentPane().add(stagedPanel, BorderLayout.CENTER);

        frame.setEnabled(true);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        stagedPanel.start();
    }
}
