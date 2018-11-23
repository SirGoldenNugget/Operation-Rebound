package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Enemy {
    private BufferedImage image;

    private Point location;
    private Point center;
    private int speed;

    public Enemy() {
        image = Game.getInstance().getChararcters().getSprite(424, 0, 35, 43);
        location = new Point(0, 0);
        center = new Point(15, 22);
        speed = 2;
    }

    public void update(Player player) {
        double angle = Math.atan2(player.getCenter().y - getCenter().y, player.getCenter().x - getCenter().x);
        location.x += speed * Math.cos(angle);
        location.y += speed * Math.sin(angle);

    }

    public void paint(Graphics2D g2d, Player player) {
        AffineTransform transform = g2d.getTransform();

        double angle = Math.atan2(player.getCenter().y - getCenter().y, player.getCenter().x - getCenter().x);

        g2d.rotate(angle, getCenter().x, getCenter().y);
        g2d.drawImage(image, location.x, location.y, Game.getInstance());
        g2d.setTransform(transform);
    }

    private Point getCenter() {
        return new Point(location.x + center.x, location.y + center.y);
    }
}
