package org.minhvu.operationrebound;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

public class Box implements Serializable {
    public double x = 0;
    public double y = 0;
    public boolean move = false;

    public String id;

    public Box() {
        id = UUID.randomUUID().toString();
    }

    public Box(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics2D g2d) {
        g2d.fillRect((int) x, (int) y, 32, 32);
    }
}
