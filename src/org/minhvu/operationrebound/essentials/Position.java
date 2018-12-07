package org.minhvu.operationrebound.essentials;

public class Position {
    public double x, y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }
}
