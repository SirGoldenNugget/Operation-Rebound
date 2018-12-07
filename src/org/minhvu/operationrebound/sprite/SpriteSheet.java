package org.minhvu.operationrebound.sprite;

import org.minhvu.operationrebound.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage spritesheet;

    public SpriteSheet(String file) {
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(Game.getInstance().getClass().getResourceAsStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        spritesheet = sprite;
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spritesheet.getSubimage(x, y, width, height);
    }

    public BufferedImage getSpritesheet() {
        return spritesheet;
    }
}
