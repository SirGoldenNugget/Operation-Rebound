package org.minhvu.operationrebound.entity;

import org.minhvu.operationrebound.Game;
import org.minhvu.operationrebound.essentials.HealthBar;
import org.minhvu.operationrebound.essentials.Position;
import org.minhvu.operationrebound.essentials.Scoreboard;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Enemy extends Entity {
    public static Enemy recent;

    private Point center;
    private double damage;
    private long damageTimer;
    private int damageTime;
    private boolean alive;

    public Enemy() {
        image = Game.getInstance().getChararcters().getSprite(424, 0, 35, 43);

        double randomSide = Math.random();

        if (randomSide >= 0.75) {
            location = new Position((int) (Math.random() * Game.getInstance().getWidth()), -image.getHeight(Game.getInstance()));
        } else if (randomSide >= 0.5) {
            location = new Position((int) (Math.random() * Game.getInstance().getWidth()), Game.getInstance().getHeight());
        } else if (randomSide >= 0.25) {
            location = new Position(-image.getWidth(Game.getInstance()), (int) (Math.random() * Game.getInstance().getHeight()));
        } else {
            location = new Position(Game.getInstance().getWidth(), (int) (Math.random() * Game.getInstance().getHeight()));
        }

        speed = 2;
        maxHealth = 100;
        health = maxHealth;

        center = new Point(15, 22);
        damage = 10;
        damageTimer = System.currentTimeMillis();
        damageTime = 500;
        alive = true;
    }

    public void update() {
        Player player = Game.getInstance().getPlayer();
        double angle = Math.atan2(player.getCenter().y - getCenter().y, player.getCenter().x - getCenter().x);

        if (location.x + speed * Math.cos(angle) < Game.getInstance().getWidth() && location.x + speed * Math.cos(angle) > -image.getWidth(Game.getInstance())) {
            location.x += speed * Math.cos(angle);

            if (hasCollision()) {
                location.x -= speed * Math.cos(angle);
            }

            for (Object object : Game.getInstance().getEnemies()) {
                Enemy enemy = (Enemy) object;

                if (enemy.equals(this)) {
                    break;
                }

                if (getBounds().intersects(enemy.getBounds())) {
                    location.x -= speed * Math.cos(angle);
                    break;
                }
            }
        }

        if (location.y + speed * Math.sin(angle) < Game.getInstance().getHeight() && location.y + speed * Math.sin(angle) > -image.getHeight(Game.getInstance())) {
            location.y += speed * Math.sin(angle);

            if (hasCollision()) {
                location.y -= speed * Math.sin(angle);
            }

            for (Object object : Game.getInstance().getEnemies()) {
                Enemy enemy = (Enemy) object;

                if (enemy.equals(this)) {
                    break;
                }

                if (getBounds().intersects(enemy.getBounds())) {
                    location.y -= speed * Math.sin(angle);
                    break;
                }
            }
        }

        if (getBounds().intersects(player.getBounds())) {
            if (System.currentTimeMillis() - damageTimer > damageTime) {
                damageTimer = System.currentTimeMillis();
                player.damage(damage);

                Game.getInstance().getSound().BITE.setFramePosition(0);
                Game.getInstance().getSound().BITE.start();
            }
        }
    }

    public void paint(Graphics2D g2d) {
        if (recent != null && recent.isAlive()) {
            HealthBar.paint(g2d, recent);
        }

        AffineTransform transform = g2d.getTransform();

        double angle = Math.atan2(Game.getInstance().getPlayer().getCenter().y - getCenter().y, Game.getInstance().getPlayer().getCenter().x - getCenter().x);

        g2d.rotate(angle, getCenter().x, getCenter().y);
        g2d.drawImage(image, location.getX(), location.getY(), Game.getInstance());
        g2d.setTransform(transform);
    }

    @Override
    public void damage(double damage) {
        health -= damage;

        if (health <= 0) {
            Scoreboard.score++;
            alive = false;

            Game.getInstance().getSound().DEATH.setFramePosition(0);
            Game.getInstance().getSound().DEATH.start();
        }
    }

    @Override
    public Point getCenter() {
        return new Point(location.getX() + center.x, location.getY() + center.y);
    }

    @Override
    protected Dimension getDimensions() {
        return new Dimension(image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(location.getX(), location.getY(), getDimensions().width, getDimensions().height);
    }

    public Rectangle getHeadBounds() {
        return new Rectangle(location.getX(), location.getY() + 15, 35, 15);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
