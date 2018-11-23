package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class  Entity {
    protected BufferedImage image;
    protected Position location;
    protected int speed;

    private Dimension getDimensions() {
        return new Dimension(image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
    }

    public Rectangle getBounds() {
        return new Rectangle((int)location.getX(), (int)location.getY(), getDimensions().width, getDimensions().height);
    }

    public boolean hasCollision() {
        for (int i = 0; i < Game.getInstance().getMaps().getCurrentMap().getCollisionMap().length; ++i) {
            for (int j = 0; j < Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i].length; ++j) {
                if (Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i][j] != 0 && getBounds().intersects(new Rectangle2D.Float(j * 64, i * 64, 64, 64))) {
                    return true;
                }
            }
        }

        return false;
    }
}
