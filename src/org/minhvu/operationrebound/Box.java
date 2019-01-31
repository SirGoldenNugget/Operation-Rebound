package org.minhvu.operationrebound;

import java.awt.*;
import java.io.Serializable;

public class Box implements Serializable {
    public double x = 0;
    public double y = 0;
    public boolean move = false;

    public void paint(Graphics2D g2d) {
        g2d.fillRect((int) x, (int) y, 32, 32);
    }

//    @Override
//    public String toString() {
//        return "Box [x=" + x + ", y=" + y + ", move=" + move + "]";
//    }
}
