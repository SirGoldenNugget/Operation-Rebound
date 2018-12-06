package org.minhvu.operationrebound;

import java.awt.*;

public abstract class Scoreboard {
    public static int score = 0;
    public static int bulletsFired = 0;
    public static int bulletsHit = 0;

    public static void paint(Graphics2D g2d) {

        int accuracy = (int) ((bulletsHit / (double) bulletsFired) * 100);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("calibri", Font.BOLD, 16));
        g2d.drawString("Score: " + score + "\tAccuracy: " + accuracy, 10, 20);
    }
}
