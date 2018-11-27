package org.minhvu.operationrebound;

import java.awt.*;

public class Healthbar {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 5;
    private static final int BORDER = 0;

    public static void paint(Graphics2D g2d, Entity entity) {
        g2d.setColor(Color.GRAY);
        g2d.fillRect(entity.getCenter().x - WIDTH / 2 - BORDER / 2, entity.getLocation().y + (int) entity.getBounds().getHeight() - BORDER / 2, WIDTH + BORDER, HEIGHT + BORDER);

        if (entity.getHealth() > (int) (entity.getMaxHealth() / 4 * 3)) {
            g2d.setColor(Color.GREEN);
        } else if (entity.getHealth() > (int) (entity.getMaxHealth() / 4 * 2)) {
            g2d.setColor(Color.YELLOW);
        } else if (entity.getHealth() > (int) (entity.getMaxHealth() / 4)) {
            g2d.setColor(Color.ORANGE);
        } else if (entity.getHealth() > 0) {
            g2d.setColor(Color.RED);
        }

        g2d.fillRect(entity.getCenter().x - WIDTH / 2, entity.getLocation().y + (int) entity.getBounds().getHeight(), (int) (entity.getHealth() / entity.getMaxHealth() * WIDTH), HEIGHT);
    }
}