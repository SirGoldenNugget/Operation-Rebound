package org.minhvu.operationrebound.map;

import org.minhvu.operationrebound.sprite.SpriteSheet;

public class Map {
    private SpriteSheet spritesheet;
    private int[][] collisionMap;

    public Map(SpriteSheet spritesheet, int[][] collisionMap) {
        this.spritesheet = spritesheet;
        this.collisionMap = collisionMap;
    }

    public SpriteSheet getSpritesheet() {
        return spritesheet;
    }

    public int[][] getCollisionMap() {
        return collisionMap;
    }
}