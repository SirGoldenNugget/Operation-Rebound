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
        camera.x = Game.getInstance().getPlayer().getLocation().x - Game.getInstance().getFrame().getWidth() / 2;
        camera.y = Game.getInstance().getPlayer().getLocation().y - Game.getInstance().getFrame().getHeight() / 2;

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
