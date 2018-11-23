package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Enemy extends Entity {
    private Point center;
    private double damage = 10;
    private long damageTimer = System.currentTimeMillis();
    private int damageTime = 500;
    private boolean alive;

    public Enemy() {
        image = Game.getInstance().getChararcters().getSprite(424, 0, 35, 43);
        location = new Position(0.0, 0.0);
        center = new Point(15, 22);
        speed = 2;
        health = 10;
        alive = true;
    }

    public void update() {
        Player player = Game.getInstance().getPlayer();
        double angle = Math.atan2(player.getCenter().y - getCenter().y, player.getCenter().x - getCenter().x);

        if (location.getX() + speed * Math.cos(angle) < Game.getInstance().getWidth() - image.getWidth(Game.getInstance()) && location.getX() + speed * Math.cos(angle) > 0) {
            location.setX(location.getX() + speed * Math.cos(angle));

            if (hasCollision()) {
                location.setX(location.getX() - speed * Math.cos(angle));
            }
        }

        if (location.getY() + speed * Math.sin(angle) < Game.getInstance().getHeight() - image.getHeight(Game.getInstance()) && location.getY() + speed * Math.sin(angle) > 0) {
            location.setY(location.getY() + speed * Math.sin(angle));

            if (hasCollision()) {
                location.setY(location.getY() - speed * Math.sin(angle));
            }
        }

        if (getBounds().intersects(player.getBounds())) {
            if (System.currentTimeMillis() - damageTimer > damageTime) {
                damageTimer = System.currentTimeMillis();
                player.damage(damage);
            }
        }
    }


    public void paint(Graphics2D g2d, Player player) {
        AffineTransform transform = g2d.getTransform();

        double angle = Math.atan2(player.getCenter().y - getCenter().y, player.getCenter().x - getCenter().x);

        g2d.rotate(angle, getCenter().x, getCenter().y);
        g2d.drawImage(image, (int) location.getX(), (int) location.getY(), Game.getInstance());
        g2d.setTransform(transform);
    }

    private Point getCenter() {
        return new Point((int) (location.getX() + center.x), (int) (location.getY() + center.y));
    }

    @Override
    public void damage(double damage) {
        health -= damage;

        if (health <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }
}
