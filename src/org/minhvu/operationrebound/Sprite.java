package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
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
