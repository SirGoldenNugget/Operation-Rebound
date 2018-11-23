package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Enemy extends Entity {

    private Point center;

    public Enemy() {
        super();
        image = Game.getInstance().getChararcters().getSprite(424, 0, 35, 43);
        location = new Point(0, 0);
        center = new Point(15, 22);
        speed = 2;
    }

    public void update(Player player) {
        double angle = Math.atan2(player.getCenter().y - getCenter().y, player.getCenter().x - getCenter().x);

        if (location.x + speed * Math.cos(angle) < Game.getInstance().getWidth() - image.getWidth(Game.getInstance()) && location.x + speed * Math.cos(angle) > 0) {
            location.x += speed * Math.cos(angle);

            if (hasCollision()) {
                location.x -= speed * Math.cos(angle);
            }
        }

        if (location.y + speed * Math.sin(angle) < Game.getInstance().getHeight() - image.getHeight(Game.getInstance()) && location.y + speed * Math.sin(angle) > 0) {
            location.y += speed * Math.sin(angle);

            if (hasCollision()) {
                location.y -= speed * Math.sin(angle);
            }
        }

        if (getBounds().intersects(player.getBounds())) {

        }
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
