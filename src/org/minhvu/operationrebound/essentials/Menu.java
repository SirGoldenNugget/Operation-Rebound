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
import java.util.concurrent.CopyOnWriteArrayList;

public class Menu {
    private static final CopyOnWriteArrayList<Selection> selections = new CopyOnWriteArrayList<>();

    private static final int WIDTH = 96;
    private static final int HEIGHT = 32;
    private static final int SPACING = 16;

    private static final String START = "Start";
    private static final String EXIT = "Exit";

    private static Rectangle2D playButton = new Rectangle2D.Float((Game.getInstance().getFrame().getSize().width - WIDTH) / 2, (Game.getInstance().getFrame().getSize().height - HEIGHT) / 2, WIDTH, HEIGHT);
    private static Rectangle2D exitButton = new Rectangle2D.Float((Game.getInstance().getFrame().getSize().width - WIDTH) / 2, (float) (playButton.getY() + HEIGHT + SPACING), WIDTH, HEIGHT);

    public static void create() {
        for (Map map : Game.getInstance().getMaps().getMaps()) {
            selections.add(new Selection(map.getSpritesheet().getSpriteSheet(), new Point(100, 100), 10, 0.2));
        }
    }

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
        for (Selection selection : selections) {
            selection.paint(g2d);
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

    private static class Selection {
        private BufferedImage scaled;
        private Point position;
        private Rectangle2D background;

        public Selection(BufferedImage image, Point position, int border, double scale) {
            this.position = position;

            scaled = new BufferedImage(image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()), BufferedImage.TYPE_INT_ARGB);
            background = new Rectangle2D.Float(this.position.x - border, this.position.y - border, (float) (image.getWidth(Game.getInstance()) * scale + border * 2), (float) (image.getWidth(Game.getInstance()) * scale + (border * 2)))
            ;

            AffineTransform at = new AffineTransform();
            at.scale(scale, scale);
            scaled = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR).filter(image, scaled);
        }

        public void paint(Graphics2D g2d) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fill(background);
            g2d.drawImage(scaled, position.x, position.y, Game.getInstance());
        }
    }
}
