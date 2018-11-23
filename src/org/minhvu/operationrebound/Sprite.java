package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

class Sprites {
    private HashMap<String, Sprite> sprites = new HashMap<>();

    public Sprites() {
        sprites.put("Hitman", new Sprite(
                Game.getInstance().getChararcters().getSprite(164, 88, 49, 43),
                Game.getInstance().getChararcters().getSprite(216, 44, 49, 43),
                Game.getInstance().getChararcters().getSprite(313, 132, 39, 43),
                new Point(15, 22)));
    }

    public Sprite getSprite(String name) {
        return sprites.get(name);
    }
}

class Sprite {
    private BufferedImage pistolImage;
    private BufferedImage machineImage;
    private BufferedImage reloadImage;
    private Point center;

    public Sprite(BufferedImage pistolImage, BufferedImage machineImage, BufferedImage reloadImage, Point center) {
        this.pistolImage = pistolImage;
        this.machineImage = machineImage;
        this.reloadImage = reloadImage;
        this.center = center;
    }

    public BufferedImage getPistolImage() {
        return pistolImage;
    }

    public BufferedImage getMachineImage() {
        return machineImage;
    }

    public BufferedImage getReloadImage() {
        return reloadImage;
    }

    public Point getCenter() {
        return center;
    }
}