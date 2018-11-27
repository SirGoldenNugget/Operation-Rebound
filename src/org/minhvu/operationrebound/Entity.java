package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected BufferedImage image;
    protected Position location;
    protected Point center;
    protected int speed;
    protected double health;
    protected double maxHealth;

    public Entity() {
        location = new Position(0.0, 0.0);
        center = new Point(0, 0);
    }

    public Point getLocation() {
        return new Point(location.getX(), location.getY());
    }

    protected Dimension getDimensions() {
        return new Dimension(image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
    }

    public Rectangle getBounds() {
        return new Rectangle(location.getX(), location.getY(), getDimensions().width, getDimensions().height);
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

    public double getMaxHealth() {
        return maxHealth;
    }

    public abstract void damage(double damage);

    public abstract Point getCenter();
}
