package org.minhvu.operationrebound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {
    // Used For Accessing JPanel Method.
    private static Game instance;
    private boolean running = false;
    private Thread thread;

    private Spritesheet characters;
    private Spritesheet tiles;
    private Maps maps;

    // Objects Used In The Game.
    private Player player;
    private CopyOnWriteArrayList<Bullet> bullets;

    // State Of The Game.

    // Menu For The Game.

    // For The End Game.

    // Keeping Score.

    // Used For Keeping Count Of Objects.
    private long timer = System.currentTimeMillis();

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
        characters = new Spritesheet("/spritesheet_characters.png");
        tiles = new Spritesheet("/spritesheet_tiles.png");
        maps = new Maps();
        maps.setCurrentMap("Suburbia");

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

        // Movement Of All Objects.
        player.move();

//		for (Bullet bullet : bullets)
//		{
//			bullet.move();
//		}

        Iterator<Bullet> bulletIterator = bullets.iterator();

        while (bulletIterator.hasNext()) {
            bulletIterator.next().move();
        }

        bullets.removeIf(bullet -> !bullet.isAlive());

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

        g2d.drawImage(maps.getMap("Suburbia").getSpritesheet().getSpritesheet(), 0, 0, Game.getInstance());

        for (Bullet bullet : bullets) {
            bullet.paint(g2d);
        }

        player.paint(g2d);
    }

    public Spritesheet getChararcters() {
        return characters;
    }

    public Maps getMaps() {
        return maps;
    }

    public Spritesheet getTiles() {
        return tiles;
    }

    public Player getPlayer() {
        return player;
    }

    public CopyOnWriteArrayList getBullets() {
        return bullets;
    }
}