package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player {
    private Sprite sprite;
    private BufferedImage image;

    private Point location;
    private int speed;

    private boolean uppressed;
    private boolean downpressed;
    private boolean leftpressed;
    private boolean rightpressed;

    private int ammo;
    private int maxAmmo;
    private int reloadTime;
    private long reloadStart;
    private boolean reloading;

    public Player() {
        sprite = new Sprites().getSprite("Hitman");
        image = sprite.getPistolImage();

        location = new Point((Game.getInstance().getWidth() - image.getWidth(Game.getInstance())) / 2, Game.getInstance().getHeight() - 200);
        speed = 4;

        uppressed = false;
        downpressed = false;
        leftpressed = false;
        rightpressed = false;

        maxAmmo = 15;
        ammo = maxAmmo;
        reloadTime = 1336;
        reloading = false;
    }

    public void paint(Graphics2D g2d) {
        AffineTransform transform = g2d.getTransform();

        Point mousePostion = MouseInfo.getPointerInfo().getLocation();
        double angle = Math.atan2(mousePostion.y - getCenter().y, mousePostion.x - getCenter().x);

        g2d.rotate(angle, getCenter().x, getCenter().y);
        g2d.drawImage(image, location.x, location.y, Game.getInstance());
        g2d.setTransform(transform);
    }

    public Point getCenter() {
        return new Point(location.x + 15, location.y + 22);
    }

    public Dimension getDimensions() {
        return new Dimension(image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
    }

    public void update() {
        move();

        if (reloading) {
            image = sprite.getReloadImage();
        } else {
            image = sprite.getMachineImage();
        }
    }

    private void move() {
        if (uppressed) {
            if (location.y - speed > 0) {
                location.y -= speed;
            }

            if (hasCollision()) {
                location.y += speed;
            }
        }

        if (downpressed) {
            if (location.y + speed < Game.getInstance().getHeight() - image.getHeight(Game.getInstance())) {
                location.y += speed;
            }

            if (hasCollision()) {
                location.y -= speed;
            }
        }

        if (leftpressed) {
            if (location.x - speed > 0) {
                location.x -= speed;
            }

            if (hasCollision()) {
                location.x += speed;
            }
        }

        if (rightpressed) {
            if (location.x + speed < Game.getInstance().getWidth() - image.getWidth(Game.getInstance())) {
                location.x += speed;
            }

            if (hasCollision()) {
                location.x -= speed;
            }
        }
    }

    public boolean hasCollision() {
        for (int i = 0; i < Game.getInstance().getMaps().getCurrentMap().getCollisionMap().length; ++i) {
            for (int j = 0; j < Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i].length; ++j) {
                if (Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i][j] != 0 && getBounds().intersects(new Rectangle2D.Float(j * 64, i * 64, 64, 64))) {
                    return true;
                }
            }
        }

        return false;
    }

    public Rectangle getBounds() {
        return new Rectangle(location.x, location.y, getDimensions().width, getDimensions().height);
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (ammo > 0) {
                Point mousePostion = MouseInfo.getPointerInfo().getLocation();
                double angle = Math.atan2(mousePostion.y - getCenter().y, mousePostion.x - getCenter().x);
                Game.getInstance().getBullets().add(new Bullet(new Point((int) (getCenter().x + 24 * Math.cos(angle) + 5 * -Math.sin(angle)),
                        (int) (getCenter().y + 24 * Math.sin(angle) + 5 * Math.cos(angle))), 10, 500, angle));

                if (--ammo == 0) {
                    reload();
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            uppressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            downpressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            leftpressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            rightpressed = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            uppressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            downpressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            leftpressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            rightpressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            reload();
        }
    }

    private void reload() {
        if (!reloading && ammo != maxAmmo) {
            reloading = true;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            ammo = maxAmmo;
                            reloading = false;
                        }
                    }, reloadTime
            );
        }
    }

    public Point getLocation() {
        return location;
    }
}

