package org.minhvu.operationrebound;

import java.awt.*;

public abstract class Scoreboard {
    public static void paint(Graphics2D g2d) {
        g2d.setFont(new Font("calibri", Font.BOLD, 16));
        g2d.drawString("Score: " + Game.getInstance().getPlayer().getScore() + " Accuracy: " + (int) ((Bullet.bulletsFired / Bullet.bulletsHit) * 100.0), 10, 10);
    }
}
