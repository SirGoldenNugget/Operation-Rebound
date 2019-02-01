package org.minhvu.operationrebound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

/**
 * A simple Swing-based client for the chat server. Graphically it is a frame with a text
 * field for entering messages and a textarea to see the whole dialog.
 * <p>
 * The client follows the following Chat Protocol. When the server sends "SUBMITNAME" the
 * client replies with the desired screen name. The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are already in use. When the
 * server sends a line beginning with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all chatters connected to the
 * server. When the server sends a line beginning with "MESSAGE " then all characters
 * following this string should be displayed in its message area.
 */
public class Client extends JPanel implements Runnable {
    private UUID id;
    private JFrame frame;
    private boolean running = false;
    private Thread thread;

    /**
     * Constructs the client by laying out the GUI and registering a listener with the
     * textfield so that pressing Return in the listener sends the textfield contents
     * to the server. Note however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED message from
     * the server.
     */
    public Client() {
        id = UUID.randomUUID();

        frame = new JFrame("Operation Rebound");
        frame.add(this);
        frame.setSize(800, 800);
        frame.setExtendedState(JFrame.NORMAL);
        frame.setUndecorated(false);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        KeyListener keylistener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        addKeyListener(keylistener);
        setFocusable(true);

        start();
    }

    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        thread = new Thread(this);
        thread.start();
    }

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
    }

    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) {
        new Client();
    }

    /**
     * Connects to the server then enters the processing loop.
     */
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

    private void update() {
        // Make connection and initialize streams
        try {
            Socket socket = new Socket("localhost", 9001);

            Object in = Network.deserialize(socket.getInputStream().readAllBytes());
            Object out = Network.serialize(socket.getOutputStream());
            socket.getOutputStream().
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Process all messages from server, according to the protocol.
            while (true) {
                repaint();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}