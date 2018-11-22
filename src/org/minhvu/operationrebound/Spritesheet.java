package org.minhvu.operationrebound;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Spritesheet {
    private BufferedImage spritesheet;

    public Spritesheet(String file) {
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
