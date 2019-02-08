package org.minhvu.operationrebound.essentials;

import org.minhvu.operationrebound.Game;

import java.awt.*;

public class Camera {
    private Position camera;
    private Position maxOffset;
    private Position minOffset;

    public Camera() {
        camera = new Position(0.0, 0.0);
        maxOffset = new Position(Game.getInstance().getMaps().getCurrentMap().getSize().width - Game.getInstance().getFrame().getWidth(),
                Game.getInstance().getMaps().getCurrentMap().getSize().height - Game.getInstance().getFrame().getHeight());
        minOffset = new Position(0.0, 0.0);
    }

    public void update() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
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

//        camera.x = Game.getInstance().getPlayer().getLocation().x - Game.getInstance().getFrame().getWidth() / 2;
//        camera.x = Game.getInstance().getPlayer().getLocation().x - Game.getInstance().getFrame().getWidth() / 2 + difference.x;
//        camera.y = Game.getInstance().getPlayer().getLocation().y - Game.getInstance().getFrame().getHeight() / 2;
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
//        if (camera.x > maxOffset.x) {
//            camera.x = maxOffset.x;
//        } else if (camera.x < minOffset.x) {
//            camera.x = minOffset.x;
//        }
//
//        if (camera.y > maxOffset.y) {
//            camera.y = maxOffset.y;
//        } else if (camera.y < minOffset.y) {
//            camera.y = minOffset.y;
//        }

        double dist = 0.1;
        camera.x = (mousePosition.x - Game.getInstance().getPlayer().getLocation().x) * dist;
        camera.y = (mousePosition.y - Game.getInstance().getPlayer().getLocation().y) * dist;
        System.out.println(Game.getInstance().getPlayer().getLocation().x + " " + Game.getInstance().getPlayer().getLocation().y);
        System.out.println(camera.x + " " + camera.y);
    }

    public Point getDisplacement() {
        return new Point((int) -camera.x, (int) -camera.y);
    }
}
