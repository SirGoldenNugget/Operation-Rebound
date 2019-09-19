package org.minhvu.operationrebound.entity;

import org.minhvu.operationrebound.Game;
import org.minhvu.operationrebound.essentials.Scoreboard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PowerUp {
    private static final Color[] colors = {Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE};
    private static BufferedImage[] images;

    static {
        try {
            images = new BufferedImage[]{
                        ImageIO.read(Game.getInstance().getClass().getResourceAsStream("/sprites/Power Ups/speedx48.jpg")),
                        ImageIO.read(Game.getInstance().getClass().getResourceAsStream("/sprites/Power Ups/healthx48.jpg")),
                        ImageIO.read(Game.getInstance().getClass().getResourceAsStream("/sprites/Power Ups/damagex48.jpg")),
                        ImageIO.read(Game.getInstance().getClass().getResourceAsStream("/sprites/Power Ups/infinityx48.jpg")),
                };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int HEALTH = 50;
    private static final int DAMAGE = 100;
    private static final int DAMAGE_DURATION = 5000;
    private static final int SPEED = 2;
    private static final int SPEED_DURATION = 5000;
    private static final int MAX_AMMO = 100;
    private static final int MAX_AMMO_DURATION = 5000;

    private Point position;
    private Dimension dimension;
    private long timer;
    private int duration;
    private boolean alive;
    private Color color;
    private int index;

    public PowerUp() {
        int[][] collisionMap = Game.getInstance().getMaps().getCurrentMap().getCollisionMap();

        int r = (int) (Math.random() * collisionMap[0].length);
        int c = (int) (Math.random() * collisionMap.length);

        while (collisionMap[c][r] != 0) {
            r = (int) (Math.random() * collisionMap[0].length);
            c = (int) (Math.random() * collisionMap.length);
        }

        dimension = new Dimension(48, 48);
        position = new Point(r * 64 + (64 - dimension.width) / 2, c * 64 + (64 - dimension.height) / 2);
        timer = System.currentTimeMillis();
        duration = 10000;
        alive = true;
        index = (int) (Math.random() * colors.length);
        color = colors[index];
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, dimension.width, dimension.height);
    }

    public void update() {
        if (System.currentTimeMillis() - timer > duration) {
            alive = false;
        }
    }

    public void consume(Player player) {
        if (color.equals(Color.YELLOW)) {
            player.setSpeed(player.getSpeed() + SPEED);

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            player.setSpeed(player.getSpeed() - SPEED);
                        }
                    }, SPEED_DURATION
            );

            Scoreboard.yellowPowerUps++;
        } else if (color.equals(Color.GREEN)) {
            player.setHealth(player.getHealth() + HEALTH);

            Scoreboard.greenPowerUps++;
        } else if (color.equals(Color.RED)) {
            player.setDamage(player.getDamage() + DAMAGE);

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            player.setDamage(player.getDamage() - DAMAGE);
                        }
                    }, DAMAGE_DURATION
            );

            Scoreboard.redPowerUps++;
        } else if (color.equals(Color.BLUE)) {
            player.setMaxAmmo(player.getMaxAmmo() + MAX_AMMO);
            player.setAmmo(player.getMaxAmmo());
            player.setBulletTime(60);

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            player.setMaxAmmo(player.getMaxAmmo() - MAX_AMMO);
                            player.setAmmo(player.getMaxAmmo());
                            player.setBulletTime(120);
                        }
                    }, MAX_AMMO_DURATION
            );

            Scoreboard.bluePowerUps++;
        }

        alive = false;
    }

    public void paint(Graphics2D g2d) {
//        g2d.setColor(color);
//        g2d.fillRoundRect(position.x, position.y, dimension.width, dimension.height, dimension.width / 2, dimension.height / 2);
        g2d.drawImage(images[index], position.x, position.y, Game.getInstance());
    }

    public boolean isAlive() {
        return alive;
    }
}
