package org.minhvu.operationrebound;

import org.minhvu.operationrebound.entity.Bullet;
import org.minhvu.operationrebound.entity.Enemy;
import org.minhvu.operationrebound.entity.Player;
import org.minhvu.operationrebound.entity.PowerUp;
import org.minhvu.operationrebound.essentials.Camera;
import org.minhvu.operationrebound.essentials.Menu;
import org.minhvu.operationrebound.essentials.Scoreboard;
import org.minhvu.operationrebound.essentials.Sound;
import org.minhvu.operationrebound.map.Maps;
import org.minhvu.operationrebound.sprite.SpriteSheet;

import javax.sound.sampled.Clip;
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
    private JFrame frame;
    private Camera camera;
    private boolean running = false;
    private Thread thread;

    private State state;
    private SpriteSheet characters;
    private Maps maps;
    private Sound sound;

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

        state = State.menu;

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

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (state.equals(State.menu)) {
                    Menu.mouseReleased(e);
                } else if (state.equals(State.play)) {
                    player.mouseReleased(e);
                }
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
        maps = new Maps();
        sound = new Sound();

        sound.HELL.loop(Clip.LOOP_CONTINUOUSLY);

        // Create The Frame.
        frame = new JFrame("Operation Rebound");
        frame.add(this);
//        frame.setSize(800, 800);
//        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
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

        camera = new Camera();

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
        if (state.equals(State.play)) {
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
            camera.update();
        }

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
        g2d.setBackground(Color.BLACK);

        super.paint(g2d);

        g2d.translate(camera.getDisplacement().x, camera.getDisplacement().y);
        g2d.drawImage(maps.getCurrentMap().getSpritesheet().getSpritesheet(), 0, 0, Game.getInstance());

        if (state.equals(State.play)) {
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
        } else if (state.equals(State.menu)) {
            Menu.paint(g2d);
        } else if (state.equals(State.end)) {

        }

        Scoreboard.paint(g2d);
    }

    public void reset() {
        player = new Player();
        bullets = new CopyOnWriteArrayList<>();
        enemies = new CopyOnWriteArrayList<>();
        powerups = new CopyOnWriteArrayList<>();
        Scoreboard.reset();
    }

    public Frame getFrame() {
        return frame;
    }

    public SpriteSheet getChararcters() {
        return characters;
    }

    public Maps getMaps() {
        return maps;
    }

    public Sound getSound() {
        return sound;
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

    public Point getMousePosition() {
        return new Point(MouseInfo.getPointerInfo().getLocation().x - camera.getDisplacement().x, MouseInfo.getPointerInfo().getLocation().y - camera.getDisplacement().y);
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        menu,
        play,
        end
    }
}