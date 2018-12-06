package org.minhvu.operationrebound;

import java.awt.*;
import java.util.HashMap;

public class Sprites {
    private HashMap<String, Sprite> sprites = new HashMap<>();

    public Sprites() {
        sprites.put("Hitman", new Sprite(
                Game.getInstance().getChararcters().getSprite(164, 88, 49, 43),
                Game.getInstance().getChararcters().getSprite(215, 44, 49, 43),
                Game.getInstance().getChararcters().getSprite(313, 132, 39, 43),
                new Point(15, 22)));
        sprites.put("Blue", new Sprite(
                Game.getInstance().getChararcters().getSprite(263, 132, 49, 43),
                Game.getInstance().getChararcters().getSprite(212, 176, 49, 43),
                Game.getInstance().getChararcters().getSprite(309, 0, 39, 43),
                new Point(15, 22)));
        sprites.put("Brown", new Sprite(
                Game.getInstance().getChararcters().getSprite(262, 176, 49, 43),
                Game.getInstance().getChararcters().getSprite(214, 88, 49, 43),
                Game.getInstance().getChararcters().getSprite(312, 176, 39, 43),
                new Point(15, 22)));
        sprites.put("Old", new Sprite(
                Game.getInstance().getChararcters().getSprite(216, 0, 49, 43),
                Game.getInstance().getChararcters().getSprite(213, 132, 49, 43),
                Game.getInstance().getChararcters().getSprite(307, 144, 39, 43),
                new Point(15, 22)));
        sprites.put("Robot", new Sprite(
                Game.getInstance().getChararcters().getSprite(164, 44, 49, 43),
                Game.getInstance().getChararcters().getSprite(166, 0, 49, 43),
                Game.getInstance().getChararcters().getSprite(306, 88, 39, 43),
                new Point(15, 22)));
        sprites.put("Soldier", new Sprite(
                Game.getInstance().getChararcters().getSprite(113, 0, 52, 43),
                Game.getInstance().getChararcters().getSprite(110, 132, 52, 43),
                Game.getInstance().getChararcters().getSprite(264, 44, 42, 43),
                new Point(15, 22)));
        sprites.put("Survivor", new Sprite(
                Game.getInstance().getChararcters().getSprite(112, 88, 51, 43),
                Game.getInstance().getChararcters().getSprite(110, 176, 51, 43),
                Game.getInstance().getChararcters().getSprite(264, 88, 41, 43),
                new Point(15, 22)));
        sprites.put("Woman", new Sprite(
                Game.getInstance().getChararcters().getSprite(58, 44, 52, 43),
                Game.getInstance().getChararcters().getSprite(111, 44, 52, 43),
                Game.getInstance().getChararcters().getSprite(266, 0, 42, 43),
                new Point(15, 22)));
    }

    public Sprite getRandomSprite() {
        Object[] sprites = this.sprites.values().toArray();
        return (Sprite) sprites[(int) (this.sprites.size() * Math.random())];
    }

    public Sprite getSprite(String name) {
        return sprites.get(name);
    }
}