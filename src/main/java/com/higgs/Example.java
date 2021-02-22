package com.higgs;

import com.higgs.staged.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class Example {
    private static final Dimension SIZE = new Dimension(1366, 768);

    public static void main(final String[] args) {
        final JFrame frame = new JFrame("Example Stage");

        final JPanel contentPane = new JPanel(new BorderLayout());

        frame.setContentPane(contentPane);

        frame.setSize(Example.SIZE);
        contentPane.setSize(Example.SIZE);

        final Stage stage = new Stage(contentPane) {
            int acts = 0;

            private void init() {
                this.setImage();

                this.doAdd();
            }

            private void doAdd() {
                final StagedActor actor = new StagedActor() {
                    int acts = 0;

                    private void init() {
                        this.setImage();
                    }

                    private void setImage() {
                        final BufferedImage image = new BufferedImage(40, 100, BufferedImage.TYPE_INT_ARGB);

                        final Graphics2D g2d = image.createGraphics();
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(0, 0, 40, 100);

                        this.setAnimation(new SimpleAnimation(Animation.NO_TRANS, image));
                    }

                    @Override
                    public void act() {
                        if (this.acts == 0) this.init();
                        this.setAngle(this.getAngle() + 1);
                        this.acts++;
                    }
                };
                this.addActor(actor, 0, 0);
            }

            private void setImage() {
                final BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

                final Graphics2D g2d = image.createGraphics();
                g2d.setColor(Color.RED);
                g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

                this.setAnimation(new SimpleAnimation(Animation.NO_TRANS, image));
            }

            @Override
            public void act() {
                if (this.acts == 0) {
                    this.init();
                }
                this.acts++;
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
