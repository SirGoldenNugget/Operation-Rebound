package org.minhvu.operationrebound.essentials;

import org.minhvu.operationrebound.Game;
import org.minhvu.operationrebound.map.Map;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Menu {
    private static final int WIDTH = 96;
    private static final int HEIGHT = 32;
    private static final int SPACING = 16;
    private static final String START = "Start";
    private static final String EXIT = "Exit";
    private static Rectangle2D playButton = new Rectangle2D.Float((Game.getInstance().getFrame().getSize().width - WIDTH) / 2, (Game.getInstance().getFrame().getSize().height - HEIGHT) / 2, WIDTH, HEIGHT);
    private static Rectangle2D exitButton = new Rectangle2D.Float((Game.getInstance().getFrame().getSize().width - WIDTH) / 2, (float) (playButton.getY() + HEIGHT + SPACING), WIDTH, HEIGHT);

    public static void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (playButton.contains(e.getPoint())) {
                Game.getInstance().setState(Game.State.play);
                Game.getInstance().reset();
            } else if (exitButton.contains(e.getPoint())) {
                System.exit(1);
            }
        }
    }

    public static void paint(Graphics2D g2d) {

        for (Map map : Game.getInstance().getMaps().getMaps()) {
            BufferedImage image = map.getSpritesheet().getSpriteSheet();


            BufferedImage after = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = new AffineTransform();
            at.scale(0.2, 0.2);
            g2d.fillRect(90, 90, (int) (after.getWidth() * 0.2 + 10), (int) (after.getWidth() * 0.2 + 10));
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            after = scaleOp.filter(image, after);
            g2d.drawImage(after, 100, 100, Game.getInstance());
        }

        // Painting "Start" and "Exit" buttons.
        g2d.setColor(Color.DARK_GRAY);
        g2d.fill(playButton);
        g2d.fill(exitButton);
        g2d.setColor(Color.WHITE);

        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("Font.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont((float) HEIGHT);
            g2d.setFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        g2d.drawString(START, (int) playButton.getCenterX() - g2d.getFontMetrics().stringWidth(START) / 2, (int) (playButton.getCenterY() + HEIGHT / 4) + 2);
        g2d.drawString(EXIT, (int) exitButton.getCenterX() - g2d.getFontMetrics().stringWidth(EXIT) / 2, (int) (exitButton.getCenterY() + HEIGHT / 4) + 2);
    }
}
