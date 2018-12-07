package org.minhvu.operationrebound.entity;

import org.minhvu.operationrebound.Game;
import org.minhvu.operationrebound.essentials.Position;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected BufferedImage image;
    protected Position location;
    protected Point center;
    protected double speed;
    protected double health;
    protected double maxHealth;

    public Entity() {
        location = new Position(0.0, 0.0);
        center = new Point(0, 0);
    }

    public Point getLocation() {
        return new Point(location.getX(), location.getY());
    }

    protected boolean hasCollision() {
        for (int i = 0; i < Game.getInstance().getMaps().getCurrentMap().getCollisionMap().length; ++i) {
            for (int j = 0; j < Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i].length; ++j) {
                if (Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i][j] != 0 && getBounds().intersects(new Rectangle2D.Float(j * 64, i * 64, 64, 64))) {
                    return true;
                }
            }
        }

        return false;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public abstract void damage(double damage);

    public abstract Point getCenter();

    protected abstract Dimension getDimensions();

    public abstract Rectangle getBounds();
}
