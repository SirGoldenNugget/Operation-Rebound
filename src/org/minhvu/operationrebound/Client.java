package org.minhvu.operationrebound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.*;

public class Client extends JPanel implements Runnable {
    private MulticastSocket clientSocket;
    private InetAddress address;

    private byte[] recieveData;
    private byte[] sendData;

    private Thread thread;
    private JFrame frame;
    private boolean running;

    private Box box = new Box();

    public Client() throws IOException {
        clientSocket = new MulticastSocket(10000);
        address = InetAddress.getByName("230.0.0.0");
        clientSocket.joinGroup(address);

        recieveData = new byte[1024];
        sendData = new byte[1024];

        KeyListener keylistener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                box.move = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                box.move = false;
            }
        };

        addKeyListener(keylistener);
        setFocusable(true);

        // Create The Frame.
        frame = new JFrame("Operation Rebound");
        frame.add(this);
        frame.setSize(800, 800);
        frame.setExtendedState(JFrame.NORMAL);
        frame.setUndecorated(false);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        start();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }

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

    public void update() {
        repaint();
    }

    private class Sender implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    sendData = Network.serialize(box);
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 10000);
                    clientSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Reciever implements Runnable{
        @Override
        public void run() {
            while (running) {
                try {
                    DatagramPacket recievePacket = new DatagramPacket(recieveData, recieveData.length);
                    clientSocket.receive(recievePacket);
                    box = (Box) Network.deserialize(recievePacket.getData());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(Color.WHITE);

        super.paint(g2d);

        box.paint(g2d);
    }

    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;

        thread = new Thread(this);
        thread.start();

        new Thread(new Sender()).start();
        new Thread(new Reciever()).start();
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;

        try {
            thread.join();
            clientSocket.leaveGroup(address);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        clientSocket.close();

        System.exit(1);
    }
}