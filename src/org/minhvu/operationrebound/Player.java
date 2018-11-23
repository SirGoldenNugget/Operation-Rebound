package org.minhvu.operationrebound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class Player extends Entity {
    private Sprite sprite;

    private boolean uppressed;
    private boolean downpressed;
    private boolean leftpressed;
    private boolean rightpressed;

    private int ammo;
    private int maxAmmo;
    private int reloadTime;
    private boolean reloading;

    public Player() {
        sprite = new Sprites().getSprite("Hitman");
        image = sprite.getPistolImage();

        location = new Position((Game.getInstance().getWidth() - image.getWidth(Game.getInstance())) / 2, Game.getInstance().getHeight() - 200);
        speed = 4;

        uppressed = false;
        downpressed = false;
        leftpressed = false;
        rightpressed = false;

        maxAmmo = 10;
        ammo = maxAmmo;
        reloadTime = 1336;
        reloading = false;
    }

    public void paint(Graphics2D g2d) {
        AffineTransform transform = g2d.getTransform();

        Point mousePostion = MouseInfo.getPointerInfo().getLocation();
        double angle = Math.atan2(mousePostion.y - getCenter().y, mousePostion.x - getCenter().x);

        g2d.rotate(angle, getCenter().x, getCenter().y);
        g2d.drawImage(image, (int) location.getX(), (int) location.getY(), Game.getInstance());
        g2d.setTransform(transform);
    }

    public Point getCenter() {
        return new Point((int) (location.getX() + sprite.getCenter().x), (int) (location.getY() + sprite.getCenter().y));
    }

    public void update() {
        if (uppressed) {
            if (location.getY() - speed > 0) {
                location.setY(location.getY() - speed);
            }

            if (hasCollision()) {
                location.setY(location.getY() + speed);
            }
        }

        if (downpressed) {
            if (location.getY() + speed < Game.getInstance().getHeight() - image.getHeight(Game.getInstance())) {
                location.setY(location.getY() + speed);
            }

            if (hasCollision()) {
                location.setY(location.getY() - speed);
            }
        }

        if (leftpressed) {
            if (location.getX() - speed > 0) {
                location.setX(location.getX() - speed);
            }

            if (hasCollision()) {
                location.setX(location.getX() + speed);
            }
        }

        if (rightpressed) {
            if (location.getX() + speed < Game.getInstance().getWidth() - image.getWidth(Game.getInstance())) {
                location.setX(location.getX() + speed);
            }

            if (hasCollision()) {
                location.setX(location.getX() - speed);
            }
        }

        if (reloading) {
            image = sprite.getReloadImage();
        } else {
            image = sprite.getMachineImage();
        }
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
}

