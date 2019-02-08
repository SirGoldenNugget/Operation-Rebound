package org.minhvu.operationrebound.essentials;

import org.minhvu.operationrebound.Game;

import java.awt.*;

public class Camera {
    private Position camera;
    private Position maxOffset;
    private Position minOffset;

    public Camera() {
        camera = new Position(Game.getInstance().getPlayer().getLocation().x, Game.getInstance().getPlayer().getLocation().y);
        maxOffset = new Position(Game.getInstance().getMaps().getCurrentMap().getSize().width - Game.getInstance().getFrame().getWidth(),
                Game.getInstance().getMaps().getCurrentMap().getSize().height - Game.getInstance().getFrame().getHeight());
        minOffset = new Position(0.0, 0.0);
    }

    public void update() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
//        camera.x = Game.getInstance().getPlayer().getLocation().x - Game.getInstance().getFrame().getWidth() / 2;
//        camera.y = Game.getInstance().getPlayer().getLocation().y - Game.getInstance().getFrame().getHeight() / 2;
//        Point difference = new Point(mousePosition.x - Game.getInstance().getPlayer().getCenter().x, mousePosition.y - Game.getInstance().getPlayer().getCenter().y);

//        System.out.println(difference.x + " " + difference.y);
//        System.out.println(Game.getInstance().getFrame().getWidth() + " " + Game.getInstance().getFrame().getHeight());

//        if (difference.x > Game.getInstance().getFrame().getWidth() * 0.25) {
//            difference.x = (int) (Game.getInstance().getFrame().getWidth() * 0.25);
//        }
//
//        if (difference.y > Game.getInstance().getFrame().getHeight() * 0.25) {
//            difference.y = (int) (Game.getInstance().getFrame().getHeight() * 0.25);
//        }

//        camera.x = Game.getInstance().getPlayer().getLocation().x - Game.getInstance().getFrame().getWidth() / 2 + difference.x;
//        camera.y = Game.getInstance().getPlayer().getLocation().y - Game.getInstance().getFrame().getHeight() / 2 + difference.y;
//
//
//        Point difference = new Point(mousePosition.x - Game.getInstance().getPlayer().getCenter().x + camera.getX(), mousePosition.y - Game.getInstance().getPlayer().getCenter().y + camera.getY());
//
//        if (difference.x > Game.getInstance().getFrame().getWidth() * 0.25) {
//            difference.x = (int) (Game.getInstance().getFrame().getWidth() * 0.25);
//        }
//        if (difference.y > Game.getInstance().getFrame().getHeight() * 0.25) {
//            difference.y = (int) (Game.getInstance().getFrame().getHeight() * 0.25);
//        }
//
//
//        camera.x = Game.getInstance().getPlayer().getLocation().x - Game.getInstance().getFrame().getWidth() / 2 + difference.x;
//        camera.y = Game.getInstance().getPlayer().getLocation().y - Game.getInstance().getFrame().getHeight() / 2 + difference.y;
//


        double dist = 0.25;

        System.out.println("Camera Before: " + camera.x + ", " + camera.y);
        System.out.println("Player Location: " + Game.getInstance().getPlayer().getLocation().x + ", " + Game.getInstance().getPlayer().getLocation().y);
        System.out.println("Mouse Position: " + mousePosition.x + ", " + mousePosition.y);

        camera.x = (mousePosition.x + camera.x - Game.getInstance().getPlayer().getLocation().x) * dist + Game.getInstance().getPlayer().getLocation().x - Game.getInstance().getFrame().getSize().width / 2;
        camera.y = (mousePosition.y + camera.y - Game.getInstance().getPlayer().getLocation().y) * dist + Game.getInstance().getPlayer().getLocation().y - Game.getInstance().getFrame().getSize().height / 2;

        System.out.println("Camera After: " + camera.x + ", " + camera.y);

        if (camera.x > maxOffset.x) {
            camera.x = maxOffset.x;
        } else if (camera.x < minOffset.x) {
            camera.x = minOffset.x;
        }

        if (camera.y > maxOffset.y) {
            camera.y = maxOffset.y;
        } else if (camera.y < minOffset.y) {
            camera.y = minOffset.y;
        }
    }

    public Point getDisplacement() {
        return new Point((int) -camera.x, (int) -camera.y);
    }
}
