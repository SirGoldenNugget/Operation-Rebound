package org.minhvu.operationrebound.sprite;

import org.minhvu.operationrebound.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage spriteSheet;

    public SpriteSheet(String file) {
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(Game.getInstance().getClass().getResourceAsStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        spriteSheet = sprite;
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y, width, height);
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }
}
