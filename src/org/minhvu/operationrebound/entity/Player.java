package org.minhvu.operationrebound.entity;

import org.minhvu.operationrebound.Game;
import org.minhvu.operationrebound.essentials.HealthBar;
import org.minhvu.operationrebound.essentials.Scoreboard;
import org.minhvu.operationrebound.sprite.Sprite;
import org.minhvu.operationrebound.sprite.Sprites;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class Player extends Entity {
    private Sprite sprite;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private boolean fullAuto;
    private boolean mousePressed;

    private double damage;
    private int ammo;
    private int maxAmmo;
    private int reloadTime;
    private boolean reloading;
    private long bulletTimer;
    private long bulletTime;
    private double multiplier;

    private long regenTimer;
    private int regenTime;
    private int regen;

    private boolean alive;

    public Player() {
        sprite = new Sprites().getRandomSprite();

        image = sprite.getPistolImage();

        location = Game.getInstance().getMaps().getCurrentMap().getPlayerStart();
        speed = 3;

        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;

        fullAuto = false;
        mousePressed = false;

        maxHealth = 100;
        health = maxHealth;
        damage = 10;
        maxAmmo = 16;
        ammo = maxAmmo;
        reloadTime = 886;
        reloading = false;
        bulletTimer = System.currentTimeMillis();
        bulletTime = 120;
        multiplier = 3;

        regenTimer = System.currentTimeMillis();
        regenTime = 500;
        regen = 1;

        alive = true;
    }

    public void paint(Graphics2D g2d) {
        AffineTransform transform = g2d.getTransform();

        HealthBar.paint(g2d, this);

//        Point mousePostion = MouseInfo.getPointerInfo().getLocation();
//        double angle = Math.atan2(mousePostion.y - getCenter().y, mousePostion.x - getCenter().x);
        double angle = Math.atan2(Game.getInstance().getMousePosition().y - getCenter().y, Game.getInstance().getMousePosition().x - getCenter().x);

        g2d.rotate(angle, getCenter().x, getCenter().y);
        g2d.drawImage(image, location.getX(), location.getY(), Game.getInstance());
        g2d.setTransform(transform);
    }

    public void update() {
        if (upPressed) {
            double speed = (leftPressed || rightPressed) ? this.speed * Math.sqrt(2) / 2 : this.speed;

            if (location.y - speed > 0) {
                location.y -= speed;
            }

            if (hasCollision()) {
                location.y += speed;
            }
        }

        if (downPressed) {
            double speed = (leftPressed || rightPressed) ? this.speed * Math.sqrt(2) / 2 : this.speed;

            if (location.y + speed < Game.getInstance().getMaps().getCurrentMap().getSize().height - image.getHeight(Game.getInstance())) {
                location.y += speed;
            }

            if (hasCollision()) {
                location.y -= speed;
            }
        }

        if (leftPressed) {
            double speed = (upPressed || downPressed) ? this.speed * Math.sqrt(2) / 2 : this.speed;

            if (location.x - speed > 0) {
                location.x -= speed;
            }

            if (hasCollision()) {
                location.x += speed;
            }
        }

        if (rightPressed) {
            double speed = (upPressed || downPressed) ? this.speed * Math.sqrt(2) / 2 : this.speed;

            if (location.x + speed < Game.getInstance().getMaps().getCurrentMap().getSize().width - image.getWidth(Game.getInstance())) {
                location.x += speed;
            }

            if (hasCollision()) {
                location.x -= speed;
            }
        }

        if (reloading) {
            image = sprite.getReloadImage();
        } else {
            image = sprite.getMachineImage();
        }

        // Update Power Ups
        for (Object object : Game.getInstance().getPowerUps()) {
            PowerUp powerup = (PowerUp) object;

            if (powerup.isAlive() && powerup.getBounds().contains(getCenter())) {
                powerup.consume(this);
            }
        }

        // Update Health
        if (health < maxHealth && System.currentTimeMillis() - regenTimer > regenTime) {
            health += regen;
            regenTimer = System.currentTimeMillis();
        }

        if (health > maxHealth) {
            health = maxHealth;
        }

        if (mousePressed && ammo > 0 && !reloading && System.currentTimeMillis() - bulletTimer > bulletTime) {
            double angle = Math.atan2(Game.getInstance().getMousePosition().y - getCenter().y, Game.getInstance().getMousePosition().x - getCenter().x);

            Game.getInstance().getBullets().add(new Bullet(new Point((int) (getCenter().x + 24 * Math.cos(angle) + 5 * -Math.sin(angle)),
                    (int) (getCenter().y + 24 * Math.sin(angle) + 5 * Math.cos(angle))), damage, 10, 500, angle, multiplier));

            Game.getInstance().getSound().FIRE.setFramePosition(0);
            Game.getInstance().getSound().FIRE.start();

            if (--ammo == 0) {
                reload();
            }

            bulletTimer = System.currentTimeMillis();
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mousePressed = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mousePressed = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            reload();
        }
    }

    private void reload() {
        if (!reloading && ammo != maxAmmo) {
            reloading = true;

            Game.getInstance().getSound().START_RELOAD.setFramePosition(0);
            Game.getInstance().getSound().START_RELOAD.start();

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            ammo = maxAmmo;
                            reloading = false;

                            Game.getInstance().getSound().FINISH_RELOAD.setFramePosition(0);
                            Game.getInstance().getSound().FINISH_RELOAD.start();

                            Scoreboard.reloads++;
                        }
                    }, reloadTime
            );
        }
    }

    public void setBulletTime(int bulletTime) {
        this.bulletTime = bulletTime;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    public void damage(double damage) {
        health -= damage;
        Scoreboard.damageRecieved += damage;

        if (health <= 0) {
            Scoreboard.output();
            alive = false;
            Game.getInstance().setState(Game.State.menu);
        }
    }

    @Override
    public Point getCenter() {
        return new Point(location.getX() + sprite.getCenter().x, location.getY() + sprite.getCenter().y);
    }

    @Override
    protected Dimension getDimensions() {
        return new Dimension(sprite.getMachineImage().getWidth(Game.getInstance()), sprite.getMachineImage().getHeight(Game.getInstance()));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(location.getX(), location.getY(), getDimensions().width - 16, getDimensions().height);
    }

    public boolean isAlive() {
        return alive;
    }
}

