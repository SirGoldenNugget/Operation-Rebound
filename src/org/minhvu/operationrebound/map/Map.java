package org.minhvu.operationrebound.map;

import org.minhvu.operationrebound.essentials.Position;
import org.minhvu.operationrebound.sprite.SpriteSheet;

import java.awt.*;

public class Map {
    private SpriteSheet spritesheet;
    private int[][] collisionMap;
    private Position playerStart;

    public Map(SpriteSheet spritesheet, int[][] collisionMap) {
        this.spritesheet = spritesheet;
        this.collisionMap = collisionMap;

        for (int i = 0; i < this.collisionMap.length; ++i) {
            for (int j = 0; j < this.collisionMap[0].length; ++j) {
                if (collisionMap[i][j] == Maps.start) {
                    playerStart = new Position(j * 64 + (64 - 49) / 2, i * 64 + (64 - 43) / 2);
                }
            }
        }
    }

    public SpriteSheet getSpritesheet() {
        return spritesheet;
    }

    public int[][] getCollisionMap() {
        return collisionMap;
    }

    public Position getPlayerStart() {
        return playerStart;
    }

    public Dimension getSize() {
        return new Dimension(collisionMap[0].length * 64, collisionMap.length * 64);
    }
}