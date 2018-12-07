package org.minhvu.operationrebound;

import org.minhvu.operationrebound.entity.Bullet;
import org.minhvu.operationrebound.entity.Enemy;
import org.minhvu.operationrebound.entity.Player;
import org.minhvu.operationrebound.entity.PowerUp;
import org.minhvu.operationrebound.essentials.Scoreboard;
import org.minhvu.operationrebound.map.Maps;
import org.minhvu.operationrebound.sprite.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {
    // Used For Accessing JPanel Method.
    private static Game instance;
    private boolean running = false;
    private Thread thread;

    private SpriteSheet characters;
    private SpriteSheet tiles;
    private Maps maps;

    // Objects Used In The Game.
    private Player player;
    private CopyOnWriteArrayList<Bullet> bullets;
    private CopyOnWriteArrayList<Enemy> enemies;
    private CopyOnWriteArrayList<PowerUp> powerups;

    // Used For Keeping Count Of Objects.
    private long respawnTimer;
    private int respawnTime;

    private long powerupTimer;
    private int powerupTime;

    // Constructor.
    public Game() {
        instance = this;

        // Anonymous Use Of Keyboard Input.
        KeyListener keylistener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }
        };

        // Anonymouse Use Of Mouse Input.
        MouseListener mouselistener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
//                player.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                player.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };

        addKeyListener(keylistener);
        addMouseListener(mouselistener);
        setFocusable(true);

        // Load In The Sprite Sheets.
        characters = new SpriteSheet("/spritesheet_characters.png");
        tiles = new SpriteSheet("/spritesheet_tiles.png");
        maps = new Maps();
        maps.setCurrentMap("Stonecold");

        // Create The Frame.
        JFrame frame = new JFrame("Operation Rebound");
        frame.add(this);
        frame.setSize(1920, 1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize Everyhing.
        player = new Player();
        bullets = new CopyOnWriteArrayList<>();
        enemies = new CopyOnWriteArrayList<>();
        powerups = new CopyOnWriteArrayList<>();

        respawnTimer = System.currentTimeMillis();
        respawnTime = 2000;

        powerupTimer = System.currentTimeMillis();
        powerupTime = 5000;

        // Begins The Thread.
        start();
    }

    // Entry Point.
    public static void main(String[] args) {
        new Game();
    }

    // Getters For The Class Objects.
    public static Game getInstance() {
        return instance;
    }

    // Starts The Thread.
    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        thread = new Thread(this);
        thread.start();
    }

    // The Heart Of The Game: The Game Loop.
    @Override
    public void run() {
        long lasttime = System.nanoTime();
        final double ticks = 60.0;
        double nanoseconds = 1000000000 / ticks;
        double delta = 0;

        while (running) {
            long time = System.nanoTime();
            delta += (time - lasttime) / nanoseconds;
            lasttime = time;

            if (delta >= 1) {
                update();
                delta--;
            }
        }

        stop();
    }

    // Updates The Objects.
    private void update() {
        this.setBackground(Color.WHITE);

        for (Bullet bullet : bullets) {
            bullet.update();
        }

        bullets.removeIf(bullet -> !bullet.isAlive());

        if (System.currentTimeMillis() - respawnTimer > respawnTime) {
            enemies.add(new Enemy());
            respawnTimer = System.currentTimeMillis();
            Scoreboard.totalEnemies++;
        }

        for (Enemy enemy : enemies) {
            enemy.update();
        }

        enemies.removeIf(enemy -> !enemy.isAlive());

        if (System.currentTimeMillis() - powerupTimer > powerupTime) {
            powerups.add(new PowerUp());
            powerupTimer = System.currentTimeMillis();
            Scoreboard.totalPowerUps++;
        }

        for (PowerUp powerup : powerups) {
            powerup.update();
        }

        powerups.removeIf(powerup -> !powerup.isAlive());

        player.update();

        repaint();
    }

    // Stops The Thread.
    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(1);
    }

    // Used For Painting/Rendering Images.
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(Color.WHITE);

        super.paint(g2d);

        g2d.drawImage(maps.getCurrentMap().getSpritesheet().getSpritesheet(), 0, 0, Game.getInstance());

        for (PowerUp powerup : powerups) {
            powerup.paint(g2d);
        }

        for (Bullet bullet : bullets) {
            bullet.paint(g2d);
        }

        for (Enemy enemy : enemies) {
            enemy.paint(g2d);
        }

        player.paint(g2d);

        Scoreboard.paint(g2d);
    }

    public SpriteSheet getChararcters() {
        return characters;
    }

    public Maps getMaps() {
        return maps;
    }

    public Player getPlayer() {
        return player;
    }

    public CopyOnWriteArrayList getBullets() {
        return bullets;
    }

    public CopyOnWriteArrayList getEnemies() {
        return enemies;
    }

    public CopyOnWriteArrayList getPowerUps() {
        return powerups;
    }
}